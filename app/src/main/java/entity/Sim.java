package entity;

import exception.SimIsDeadException;
import map.House;
import map.Room;
import util.Angka;
import map.Direction;
import item.*;
import main.Game;

public class Sim extends Exception implements Runnable {
    // GAME ATTRIBUTES
    private boolean alive;

    private int x, y;
    private Game gm;

    // SIM ATTRIBUTES
    private House simHouse, currHouse;
    private Room currRoom;
    private String name;
    private int mood, hunger, health, money;
    private Job job;
    private Inventory simItems;

    // CONFIG ATTRIBUTES
    private volatile boolean hasPoop;
    private volatile int timeLastEat, timeLastSubtractPoop, poopMultiplier;
    private volatile int timeLastSleep;
    private volatile int dayLastSleep;
    private long bufferedVisitTime, bufferedWorkTime;
    private int timeChangedJob, totalJobTimeWorked;
    private Thread upgradeHouse, trackUpdates, shopQueue;
    private int timeUpgradeHouse, timeShopQueue, deliveryTime;
    
    public Sim(Game gm, House house, Room currRoom, String name) {
        this.gm = gm;
        alive = true;
        x = 5;
        y = 3;
        
        // Set basic sim info
        this.simItems = new Inventory();
        simItems.addItem(new Furniture("kasur single", -1, -1));
        simItems.addItem(new Ingredients("kentang"));
        simItems.addItem(new Dish("nasi ayam"));
        simItems.addItem(new Other("Sheet QnA"));
        simItems.addItem(new Other("HP"));
        
        
        
        this.name = name;
        this.simHouse = house;
        this.currRoom = currRoom;
        this.job = Job.createRandomJob();
        this.currHouse = simHouse;
        mood = 80;
        hunger = 80;
        health = 80;
        money = 10000;
        
        
        bufferedVisitTime = 0;
        bufferedWorkTime = 0;
        timeChangedJob = -999;
        totalJobTimeWorked = 0;
        resetTimeLastSleep(); dayLastSleep = gm.getClock().getDay();
        timeLastEat = 0;
        timeLastSubtractPoop = 0;
        hasPoop = true;
        poopMultiplier = 1;
        
        this.upgradeHouse = null;
        trackUpdates = new Thread(this);
        trackUpdates.start();
    }
    
    public int getTimeShopQueue() {return timeShopQueue;}
    public int getTimeUpgradeHouse() {return timeUpgradeHouse;}
    public int getDeliveryTime() {return deliveryTime;}
    public int getTimeLastEat() {return timeLastEat;}
    public void resetTimeLastSleep() {
        timeLastSleep = gm.getClock().getGameTime();
    }
    public int getTimeLastSleep() {return (int)timeLastSleep;}
    public String getName() {return name;}
    public House getHouse() {return simHouse;}
    public House getCurrHouse() {return currHouse;}
    public Inventory getSimItems() {return simItems;}
    public Room getRoom() {return currRoom;}
    public void setRoom(Room newRoom) {
        currRoom = newRoom;
    }
    public Job getJob() {return job;}
    public String getJobName() {return job.getName();}
    public void setJob(Job job) {
        this.job = job;
        timeChangedJob = gm.getClock().getDay();
        totalJobTimeWorked = 0;
    }
    public int getMood() {return mood;}
    public synchronized void setMood(int newMood) {
        if (!isDead()) {
            mood = newMood;
            if (mood > 100) mood = 100;
        }
    }
    public int getHunger() {return hunger;}
    public synchronized void setHunger(int newHunger) {
        if (!isDead()) {
            hunger = newHunger;
            if (hunger > 100) hunger = 100;
        }
    }
    public int getHealth() {return health;}
    public synchronized void setHealth(int newHealth) {
        if (!isDead()) {
            health = newHealth;
            if (health > 100) health = 100;
        }
    }
    public int getMoney() {return money;}
    public synchronized void setMoney(int newMoney) {
        money = newMoney;
    }
    
    
    public int getX() {return x;}
    public void setX(int newX) {x = newX;}
    public int getY() {return y;}
    public void setY(int newY) {y = newY;}
    public void setCoordinates(int x, int y) {setX(x); setY(y);}
    
    public Thread getUpgradeHouse() {return upgradeHouse;}
    public Thread getTrackUpdates() {return trackUpdates;}
    public boolean isUpgradingHouse() {return upgradeHouse != null;}
    public boolean isShoppingQueue() {return shopQueue != null;}

    public void run() {
        while (trackUpdates != null) {
            // cek alive sim, kalo ga, bunuh
            if (isDead()) {
                killSim();
                trackUpdates = null;
            }

            // track sleep
            if ((gm.getClock().getGameTime() - timeLastSleep) >= 10*60) {
                resetTimeLastSleep();
    
                setHealth(getHealth() - 5);
                setMood(getMood() - 5);
            }
    
            // sleep tiap ganti hari ke-reset
            if (dayLastSleep < gm.getClock().getDay()) {
                resetTimeLastSleep();
                dayLastSleep = (int)gm.getClock().getDay();
            }

            // track poop
            if (!hasPoop) {
                if (gm.getClock().getGameTime() - timeLastSubtractPoop >= 4*60) {
                    timeLastSubtractPoop = gm.getClock().getGameTime();

                    setMood(getMood()-5*poopMultiplier);
                    setHealth(getHealth()-5*poopMultiplier);
                    poopMultiplier++;
                }
            }
        }
    }

    /* Fungsi untuk menjalankan semua thread yang sedang berjalan di dalam Sim. Digunakan untuk melanjutkan thread setelah Load */
    public void activateRunningThread() {
        trackUpdates.run();
        if (isUpgradingHouse()) upgradeHouse.run();
        if (isShoppingQueue()) shopQueue.run();
    }

    public void changeJob(Job job) throws IllegalArgumentException {
        if (job.getName().equals(this.job.getName())) {
            throw new IllegalArgumentException(String.format("%s", "Sim tidak bisa menggati pekerjaan ke pekerjaan yang sama!"));
        }
        if (totalJobTimeWorked < 12*60) {
            throw new IllegalArgumentException(String.format("%s %s", "Sim harus bekerja setidaknya 12 menit terlebih dahulu! Sim baru bekerja selama", Angka.secToTime(totalJobTimeWorked)));
        }
        if (getMoney() < Math.round(job.getPay()/2)) throw new IllegalArgumentException("Sim harus membayar uang sebanyak " + Math.round(job.getPay()/2) + " ribu untuk mengganti pekerjaan!");
        
        // else
        setJob(job);
    }

    public boolean isDead() {
        if (health <= 0 || mood <= 0 || hunger <= 0 || !alive) return true;
        return false;
    }

    public void killSim() {
        alive = false;

        trackUpdates = null;
        upgradeHouse = null;
    }
    
    public void move(Room target) {
        currRoom = target;
    }

    public void upgradeHouse(String roomName, Room target, Direction direction) {
        if (upgradeHouse == null) {
            this.timeUpgradeHouse = gm.getClock().getGameTime();
            money -= 1500;
            upgradeHouse = new Thread(new Runnable() {
                public void run() {
                    while (upgradeHouse != null) {
                        try {
                            if (gm.getClock().getGameTime() - getTimeUpgradeHouse() >= 18 * 60 || gm.getCheat().isFastbuild()) {
                                upgradeHouse = null;
                            }
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    simHouse.addRoom(roomName, target, direction);
                }
            });
            upgradeHouse.start();
        }
    }

    public void cook(String dish) throws SimIsDeadException, IllegalArgumentException {
        switch(dish.toLowerCase()){
            case "nasi ayam":
                if(simItems.checkItemAvailable("nasi",1) && simItems.checkItemAvailable("ayam",1)){
                    simItems.removeItem(simItems.getItemsByName("nasi"));
                    simItems.removeItem(simItems.getItemsByName("ayam"));
                    simItems.addItem((new Dish(dish.toLowerCase())));
                }
                else{
                    throw new IllegalArgumentException("Bahan tidak cukup untuk memasak makanan tersebut!");
                }
                break;
            case "nasi kari":
                if(simItems.checkItemAvailable("nasi",1) && simItems.checkItemAvailable("kentang",1) && simItems.checkItemAvailable("wortel",1) && simItems.checkItemAvailable("sapi",1)){
                    simItems.removeItem(simItems.getItemsByName("nasi"));
                    simItems.removeItem(simItems.getItemsByName("kentang"));
                    simItems.removeItem(simItems.getItemsByName("wortel"));
                    simItems.removeItem(simItems.getItemsByName("sapi"));
                    simItems.addItem((new Dish(dish.toLowerCase())));
                }
                else{
                    throw new IllegalArgumentException("Bahan tidak cukup untuk memasak makanan tersebut!");
                }
                break;
            case "susu kacang":
                if(simItems.checkItemAvailable("susu",1) && simItems.checkItemAvailable("kacang",1)){
                    simItems.removeItem(simItems.getItemsByName("susu"));
                    simItems.removeItem(simItems.getItemsByName("kacang"));
                    simItems.addItem((new Dish(dish.toLowerCase())));
                }
                else{
                    throw new IllegalArgumentException("Bahan tidak cukup untuk memasak makanan tersebut!");
                }
                break;
            case "tumis sayur":
                if(simItems.checkItemAvailable("wortel",1) && simItems.checkItemAvailable("bayam",1)){
                    simItems.removeItem(simItems.getItemsByName("wortel"));
                    simItems.removeItem(simItems.getItemsByName("bayam"));
                    simItems.addItem((new Dish(dish.toLowerCase())));
                }
                else{
                    throw new IllegalArgumentException("Bahan tidak cukup untuk memasak makanan tersebut!");
                }
                break;
            case "bistik":
                if(simItems.checkItemAvailable("kentang",1) && simItems.checkItemAvailable("sapi",1)){
                    simItems.removeItem(simItems.getItemsByName("kentang"));
                    simItems.removeItem(simItems.getItemsByName("sapi"));
                    simItems.addItem((new Dish(dish.toLowerCase())));
                }
                else{
                    throw new IllegalArgumentException("Bahan tidak cukup untuk memasak makanan tersebut!");
                }
                break;
            default: throw new IllegalArgumentException("Tidak ada masakan dengan nama tersebut!");
        }

        int waktumasak = (int) Math.round(1.5*(new Dish(dish.toLowerCase())).getHungerPoint());
        setMood(getMood() + 10);

        System.out.println(String.format("Sim akan memasak selama %s", Angka.secToTime(waktumasak)));

        try {
            gm.getClock().moveTime(waktumasak * 1000);
            System.out.println("Ahhh.. senangnya memasak. +10 mood");
        } catch (SimIsDeadException e) {
            throw new SimIsDeadException("Wah sim kamu kena tumpahan minyak panas waktu lagi masak!");
        }
    }

    public void eat(String food) throws SimIsDeadException, IllegalArgumentException {
        int waktumakan = 30;

        if (!simItems.checkItemAvailable(food.toLowerCase(), 1)) {
            throw new IllegalArgumentException("Tidak ada makanan dengan nama tersebut di dalam inventory sim!");
        }

        System.out.println(String.format("Sim akan makan selama %s", Angka.secToTime(waktumakan)));

        try {
            gm.getClock().moveTime(waktumakan * 1000);
        } catch (SimIsDeadException e) {
            throw new SimIsDeadException("Sim kamu keselek waktu lagi makan!");
        }

        System.out.println(String.format("\"Abis makan gini enaknya rebahan kali ya...\" +%s hunger", ((Food)simItems.getItemsByName(food.toLowerCase())).getHungerPoint()));
        setHunger(getHunger()+ ((Food)simItems.getItemsByName(food.toLowerCase())).getHungerPoint());
        simItems.removeItem(food.toLowerCase());

        if (hasPoop) {
            hasPoop = false;
            timeLastEat = gm.getClock().getGameTime();
            timeLastSubtractPoop = timeLastEat;
            poopMultiplier = 1;            
        }
    }

    public void gamble(int money) throws IllegalArgumentException {
        // random dapet duit/kurang duit

        if (money > getMoney()) 
            throw new IllegalArgumentException(String.format("Sim tidak bisa menjudikan uang yang dia tidak punya! Uang sim adalah %s", gm.getCurrentSim().getMoney()));

        if (money < 0)
            throw new IllegalArgumentException("Tidak bisa menjudikan uang negatif!");

        int modifier = Angka.randint(-100, 100);
        int gain = money * modifier / 100;

        if (gain > 0) System.out.println(String.format("Selamat! Kamu dapet untung %s", gain));
        else if (gain < 0) System.out.println(String.format("Yahh! Kamu hilang %s", Math.abs(gain)));
        else System.out.println(String.format("Entah beruntung atau gimana tapi kamu gak hilang atau dapat duit!"));

        setMoney(getMoney() + gain);
    }

    public void read(int time) throws SimIsDeadException {
        // +10 mood -5 hunger
        // cuma bisa kelipatan 30 detik

        int cycle = time / (30);

        System.out.println("Sim akan membaca buku selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(30 * 1000);

                System.out.println("\nSim sudah membaca buku selama 30 detik, waktu tersisa: " + Angka.secToTime(time-i*30));
                System.out.println("Keren juga buku Silberschatz! -5 hunger +10 mood");
                
                setHunger(getHunger()-5);
                setMood(getMood()+10);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Buset di bukunya ada jumpscare!");
            }
        }
    }

    public void readQNA() throws SimIsDeadException {
        // random dapet buff / debuff, jalan otomatis 3 menit
        // 80% jelek, 20% bagus
        // bagus -> +30 mood, +10 health, -10 hunger
        // jelek -> -40 mood, -20 health, -30 hunger

        try {
            System.out.println("Sim akan membaca sheet QnA tubes selama " + Angka.secToTime(3*60));
            gm.getClock().moveTime(3 * 60 * 1000);
            
            int chance = Angka.randint(0, 100);
    
            if (chance >= 0 && chance < 20) { // good
                System.out.println("Wah! Tumben banget ini QnA bagus jawabannya. Sim kamu jadi semangat tubes! +30 mood +10 health -10 hunger");
    
                setMood(getMood()+30);
                setHealth(getHealth()+10);
                setHunger(getHunger()-10);
            } else { // bad
                System.out.println("ANJIR! INI QNA KOK NGACO BANGET. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA! -40 mood -20 health -30 hunger");
                
                setMood(getMood()-40);
                setHealth(getHealth()-20);
                setHunger(getHunger()-30);
            }
        } catch (SimIsDeadException e) {
            throw new SimIsDeadException("Stres karena banyak jawaban yang beda, sim kamu kena stroke...");
        }
    }

    public void playGame(int time) throws SimIsDeadException {
        // +20 mood -10 hunger -10 kesehatan / 2 menit
        // cuma bisa kelipatan 2 menit

        int cycle = time / (2*60);

        System.out.println("Sim akan bermain game selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(2 * 60 * 1000);

                System.out.println("\nSim sudah bermain game selama 2 menit, waktu tersisa: " + Angka.secToTime(time-i*2*60));
                System.out.println("Letsgo winner winner chicken dinner! -10 health -10 hunger +20 mood");
                
                setHealth(getHealth()-10);
                setHunger(getHunger()-10);
                setMood(getMood()+20);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Losestreak 20 kali... mati aja lah..");
            }
        }
    }

    public void poop() throws SimIsDeadException {
        // selalu 10 detik

        System.out.println(String.format("Sim akan buang air selama %s", Angka.secToTime(10)));
        
        try {
            gm.getClock().moveTime(10 *1000);
        } catch (SimIsDeadException e) {
            throw new SimIsDeadException("... sim mati waktu lagi berak.");
        }
        
        System.out.println(String.format("Ahh... enaknya~. +10 moode -20 hunger"));
        setHunger(getHunger()-20);
        setMood(getMood()+10);
        hasPoop = true;
    }

    public void buyItem(Item item) throws IllegalArgumentException {
        if (shopQueue == null) {
            if(item.getPriceValue() > money){
                throw new IllegalArgumentException("\nUang sim tidak cukup untuk membeli barang tersebut!");
            }
            setMoney(getMoney()-item.getPriceValue());
            timeShopQueue = gm.getClock().getGameTime();
            deliveryTime = Angka.randint(1, 5) * 30;

            System.out.println(String.format("Barang berhasil dipesan! Pesanan akan sampai dalam waktu %s", Angka.secToTime(deliveryTime)));

            shopQueue = new Thread(new Runnable() {

                public void run(){
                    while (shopQueue != null) {
                        try {
                            if (gm.getClock().getGameTime() - getTimeShopQueue() >= deliveryTime || gm.getCheat().isFastshop()) {
                                shopQueue = null;
                            }
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    simItems.addItem(item);
                }
            });
            shopQueue.start();
        } else {
            throw new IllegalArgumentException("Sim tidak bisa membeli barang karena sedang ada barang yang dipesan!");
        }
    }

    public void sellItem(String item) throws IllegalArgumentException{
        if(!simItems.checkItemAvailable(item, 1)){
            throw new IllegalArgumentException("Tidak ada barang dengan nama tersebut!");
        }
        setMoney(getMoney() + simItems.getItemsByName(item).getPriceValue());
        simItems.removeItem(item);
    }
    
    public void sleep(int time) throws SimIsDeadException {
        /*
        * PREREQUISITE: time sudah pasti kelipatan 4 menit / 240 detik
        * +30 mood, +20 kesehatan / 4 menit
        */
        int cycle = time / (60 * 4);

        System.out.println("Sim akan tidur selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(60 * 4 * 1000);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Sim terlalu nyaman di mimpinya dan gamau bangun!");
            }

            System.out.println("Sim sudah tidur selama 4 menit, waktu tersisa: " + Angka.secToTime(time-i*4*60));
            System.out.println("Gila! Tidurnya nyenyak banget! +20 health +30 mood");

            setHealth(getHealth() + 20);
            setMood(getMood() + 30);

            resetTimeLastSleep();
        }
    }

    public void visit(Sim target) throws SimIsDeadException {
        int visittime = (int) Math.round(Math.sqrt(Math.pow((this.getCurrHouse().getX() - target.getHouse().getX()), 2) + Math.pow((this.getCurrHouse().getY() - target.getHouse().getY()), 2)));
        int cycle = (int) Math.round(visittime / 30);

        System.out.println("\nSim akan berjalan selama " + visittime + " detik!");

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(30 * 1000);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Waktu lagi jalan, sim kamu jatuh ke jurang!");
            }
            System.out.println("\nSim sudah berjalan selama 30 detik, waktu tersisa: " + Angka.secToTime(visittime-30*i));
            System.out.println("Keren ya ternyata pemandangannya! -10 hunger +10 mood");
            setHunger(getHunger() - 10);
            setMood(getMood() + 10);
        }

        try {
            gm.getClock().moveTime(visittime%30 * 1000);
        } catch (SimIsDeadException e) {
            throw new SimIsDeadException("Waktu lagi jalan, sim kamu jatuh ke jurang!");
        } 
        
        // sisa waktu
        bufferedVisitTime += visittime%30;

        if (bufferedVisitTime > 30) {
            cycle = (int)(bufferedVisitTime / 30);

            System.out.println("Keren ya ternyata pemandangannya! -"+ 10*cycle +" hunger " + "+" + 10*cycle + " mood");
            setHunger(getHunger()- 10*cycle);
            setMood(getMood()+ 10*cycle);
            bufferedVisitTime %= 30;
        }

        currHouse = target.getHouse(); // ganti rumah sekarang
        currRoom = target.getHouse().getRooms().get(0); // ganti ruangan jadi ruangan awal rumah orang
    }

    public void watchTV(int time) throws SimIsDeadException {
        // +10 mood -10 hunger -5 health
        // cuma bisa kelipatan 1 menit

        int cycle = time / (60);

        System.out.println("Sim akan menonton TV selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(1 * 60 * 1000);

                System.out.println("\nSim sudah menonton TV selama 1 menit, waktu tersisa: " + Angka.secToTime(time-i*1*60));
                System.out.println("Lucu juga ya Kobo! -10 health -5 hunger +10 mood");
                
                setHealth(getHealth()-10);
                setHunger(getHunger()-5);
                setMood(getMood()+10);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Waktu lagi nonton TV tiba-tiba ada beruang yang masuk kerumah dan mukul sim!");
            }
        }
    }

    public void work(int time) throws IllegalArgumentException, SimIsDeadException {
        /*
        * PREREQUISITE : time sudah pasti kelipatan 4 menit / 120 detik
        * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
        */

        if (gm.getClock().getGameTime() - timeChangedJob < 12*60) {
            System.out.println(timeChangedJob);
            System.out.println(gm.getClock().getGameTime() - timeChangedJob);

            throw new IllegalArgumentException(("Sim harus menunggu 1 hari setelah pergantian kerja untuk bekerja! Sisa waktu tunggu " + Angka.secToTime(12*60-timeChangedJob)));
        }
        
        // else
        int cycle = time / 30;

        System.out.println("\nSim akan bekerja selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(30 * 1000); // waktu jalan 30dtk
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Sim udah gak tahan lagi... dia lompat dari lantai 20..");
            }

            System.out.println("\nSim sudah bekerja selama 30 detik, waktu tersisa: " + Angka.secToTime(time-i*30));
            System.out.println("Aduh.. capek banget kerja. -10 hunger -10 mood");

            bufferedWorkTime += 30;
            setMood(getMood() - 10);
            setHunger(getHunger() - 10);
            totalJobTimeWorked += 30;

            if (bufferedWorkTime >= 240) {
                bufferedWorkTime -= 240;
                
                System.out.println("\nSetelah 4 jam bekerja, akhirnya tugas selesai juga! +" + job.getPay() + " money");
                setMoney(getMoney() + job.getPay());
            }
        }
    }

    public void workout(int time) throws SimIsDeadException {
        /*
        * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
        */
        int cycle = time / 20;

        System.out.println("Sim akan berolahraga selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(20 * 1000);
            } catch (SimIsDeadException e) {
                throw new SimIsDeadException("Waktu lagi olahraga, ada bom nuklir jatoh di rumah sim!");
            }

            System.out.println("\nSim sudah berolahraga selama 20 detik, waktu tersisa: " + Angka.secToTime(time-i*20));
            System.out.println("UWOughh, si sim jadi lebih kuat! +5 health -5 hunger +10 mood");

            setHealth(getHealth()+5);
            setHunger(getHunger()-5);
            setMood(getMood()+10);
        }
    }

    
    public void stargaze(int time) {
        mood += (20*time/60000);
        hunger -= (15*time/60000);
        try {
            gm.getClock().moveTime(time);
        } catch (SimIsDeadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        //TODO: ROMBAK INI!
    }
}