package entity;

import java.util.*;

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
    private long timeLastPoop; //belum pake di method poop
    private long timeLastEat;  //belum pake di method eat
    private volatile long timeLastSleep;
    private volatile int dayLastSleep;

    private long bufferedVisitTime, bufferedWorkTime;
    private int timeChangedJob, totalJobTimeWorked;

    private Thread upgradeHouse, trackUpdates, shopQueue;

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
        
        this.upgradeHouse = null;
        trackUpdates = new Thread(this);
        trackUpdates.start();
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

    public Thread getUpgradeHouse() {
        return upgradeHouse;
    }
    public Thread getTrackUpdates() {return trackUpdates;}

    public int getX() {return x;}
    public void setX(int newX) {x = newX;}
    public int getY() {return y;}
    public void setY(int newY) {y = newY;}

    public void setCoordinates(int x, int y) {
        setX(x); setY(y);
    }

    public void upgradeHouse(String roomName, Room target, Direction direction) {
        if (upgradeHouse == null) {
            money -= 1500;
            upgradeHouse = new Thread(new Runnable() {
                long timeLastUpgrade = gm.getClock().getGameTime();
                public void run(){
                    while (upgradeHouse != null) {
                        try {
                            if (gm.getClock().getGameTime() - timeLastUpgrade >= 18 * 60) {
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
                throw e;
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
        
        updateSim();
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
                throw e;
            }

            System.out.println("\nSim sudah berolahraga selama 20 detik, waktu tersisa: " + Angka.secToTime(time-i*20));
            System.out.println("UWOughh, si sim jadi lebih kuat! +5 health -5 hunger +10 mood");

            setHealth(getHealth()-5);
            setHunger(getHunger()-5);
            setMood(getMood()+10);
        }

        updateSim();
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
                throw e;
            }

            System.out.println("Sim sudah tidur selama 4 menit, waktu tersisa: " + Angka.secToTime(time-i*4*60));
            System.out.println("Gila! Tidurnya nyenyak banget! +20 health +30 mood");

            setHealth(getHealth() + 20);
            setMood(getMood() + 30);

            resetTimeLastSleep();
        }

        updateSim();
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

    public void updateSim() {
        // BANGSAT INI GAJADI, GW MAU BUNUH DIRIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        // if (visiting) {
        //     if (Game.getTime() - timeLastVisitUpdate > 30) {
        //         long delta = Game.getTime() - timeLastVisitUpdate;
        //         int times = (int)delta / 30;
        //         timeLastVisitUpdate = Game.getTime();
                
        //         // penambahan tiap 30 detik
        //         setMood(getMood()+10*times);
        //         setHunger(getHunger()-10*times);
        //     }
        }

    public void eat(int time, Food food){
        /*
        * +X kekenyangan (X sesuai makanan) / siklus makan(30 detik); Makanan yang dimakan akan hilang dari inventory
        */
        if(!simItems.getItems("food").containsKey(food)){
            throw new IllegalArgumentException("\nNo food in inventory.");
        }
        for(Map.Entry<Item, Integer> e : simItems.getItems("food").entrySet()){
            if(e.getKey().getName().equals(food.getName())){
                hunger += (food.getHungerPoint()*(time%30000));
            }
        }

        try {
            gm.getClock().moveTime(time);
        } catch (SimIsDeadException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    public void cook(Food dish) {
        switch(dish.getName()){
            case "nasi ayam":
            if(simItems.checkItemAvailable("nasi",1) && simItems.checkItemAvailable("ayam",1)){
                simItems.removeItem(simItems.getItemsByName("nasi"));
                simItems.removeItem(simItems.getItemsByName("ayam"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("\nNot enough ingredients in inventory.");
            }
            break;
            case "nasi kari":
            if(simItems.checkItemAvailable("nasi",1) && simItems.checkItemAvailable("kentang",1) && simItems.checkItemAvailable("wortel",1) && simItems.checkItemAvailable("sapi",1)){
                simItems.removeItem(simItems.getItemsByName("nasi"));
                simItems.removeItem(simItems.getItemsByName("kentang"));
                simItems.removeItem(simItems.getItemsByName("wortel"));
                simItems.removeItem(simItems.getItemsByName("sapi"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("\nNot enough ingredients in inventory.");
            }
            break;
            case "susu kacang":
            if(simItems.checkItemAvailable("susu",1) && simItems.checkItemAvailable("kacang",1)){
                simItems.removeItem(simItems.getItemsByName("susu"));
                simItems.removeItem(simItems.getItemsByName("kacang"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("\nNot enough ingredients in inventory.");
            }
            break;
            case "tumis sayur":
            if(simItems.checkItemAvailable("wortel",1) && simItems.checkItemAvailable("bayam",1)){
                simItems.removeItem(simItems.getItemsByName("wortel"));
                simItems.removeItem(simItems.getItemsByName("bayam"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("\nNot enough ingredients in inventory.");
            }
            break;
            case "bistik":
            if(simItems.checkItemAvailable("kentang",1) && simItems.checkItemAvailable("sapi",1)){
                simItems.removeItem(simItems.getItemsByName("kentang"));
                simItems.removeItem(simItems.getItemsByName("sapi"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("\nNot enough ingredients in inventory.");
            }
            break;
            default: throw new IllegalArgumentException("\nNo such dish to cook.");
        }
        mood += 10;
        try {
            gm.getClock().moveTime((int) 1.5*dish.getHungerPoint());
        } catch (SimIsDeadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void visit(Sim target) throws SimIsDeadException {
        int visittime = (int)(Math.sqrt(Math.pow((this.getCurrHouse().getX() - target.getHouse().getX()), 2) + Math.pow((this.getCurrHouse().getY() - target.getHouse().getY()), 2)));
        int cycle = (int)(visittime / 30);

        System.out.println("\nSim akan berjalan selama " + visittime + " detik!");

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(30 * 1000);
            } catch (SimIsDeadException e) {
                throw e;
            }
            System.out.println("\nSim sudah berjalan selama 30 detik, waktu tersisa: " + Angka.secToTime(visittime-30*i));
            System.out.println("Keren ya ternyata pemandangannya! -10 hunger +10 mood");
            setHunger(getHunger() - 10);
            setMood(getMood() + 10);
        }

        try {
            gm.getClock().moveTime(visittime%30 * 1000);
        } catch (SimIsDeadException e) {
            throw e;
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

        updateSim();
    }

    public void poop(int time) {
        hunger-= (20*time/10000);
        mood+= (10*time/10000);
        try {
            gm.getClock().moveTime(time);
        } catch (SimIsDeadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        updateSim();
    }

    public void buyItem(Item item) {
        if (shopQueue == null) {
            if(item.getPriceValue() > money){
                throw new IllegalArgumentException("\nNot enough money");
            }
            money -= item.getPriceValue();
            shopQueue = new Thread(new Runnable() {
                long timeLastOrdered = gm.getClock().getGameTime();
                public void run(){
                    while (shopQueue != null) {
                        try {
                            if (gm.getClock().getGameTime() - timeLastOrdered >= 18 * 60) {
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
            throw new IllegalArgumentException("\nAnother order is in place.");
        }
    }

    public void sellItem(Item item) {
        if(!simItems.checkItemAvailable(item.getName(), 1)){
            throw new IllegalArgumentException("\nNot enough item to sell");
        }
        simItems.removeItem(item);
        money += item.getPriceValue();
    }

    public void move(Room target) {
        currRoom = target;
    }

    public void stargaze(int time) {
        mood += (20*time/60000);
        hunger -= (15*time/60000);
        try {
            gm.getClock().moveTime(time);
        } catch (SimIsDeadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        updateSim();

        //TODO: ROMBAK INI!
    }

    public void run() {
        while (trackUpdates != null) {
            // System.out.println(timeLastSleep);// track sleep
            if ((gm.getClock().getGameTime() - timeLastSleep) >= 10*60) {
                // System.out.println("HIT!");
                resetTimeLastSleep();
    
                setHealth(getHealth() - 5);
                setMood(getMood() - 5);
            }
    
            if (dayLastSleep < gm.getClock().getDay()) {
                resetTimeLastSleep();
                dayLastSleep = (int)gm.getClock().getDay();
            }
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
                throw e;
            }
        }
    }

    public void resetTimeLastSleep() {
        timeLastSleep = gm.getClock().getGameTime();
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
                throw e;
            }
        }
    }

    public void read(int time) throws SimIsDeadException {
        // +10 mood -5 hunger
        // cuma bisa kelipatan 30 detik

        int cycle = time / (30);

        System.out.println("Sim akan membaca buku selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            try {
                gm.getClock().moveTime(1 * 30 * 1000);

                System.out.println("\nSim sudah membaca buku selama 1 menit, waktu tersisa: " + Angka.secToTime(time-i*1*30));
                System.out.println("Keren juga buku Silberschatz! -5 hunger +10 mood");
                
                setHunger(getHunger()-5);
                setMood(getMood()+10);
            } catch (SimIsDeadException e) {
                throw e;
            }
        }
    }

    public void gamble(int money) {
        // random dapet duit/kurang duit

        int modifier = Angka.randint(-100, 100);
        int gain = money * modifier / 100;

        if (gain > 0) System.out.println(String.format("Selamat! Kamu dapet untung %s", gain));
        else if (gain < 0) System.out.println(String.format("Yahh! Kamu hilang %s", Math.abs(gain)));
        else System.out.println(String.format("Entah beruntung atau gimana tapi kamu gak hilang atau dapat duit!"));

        setMoney(getMoney() + gain);
    }

    public void readQnA() throws SimIsDeadException {
        // random dapet buff / debuff, jalan otomatis 3 menit
        // 80% jelek, 20% bagus
        // bagus -> +30 mood, +10 health, -10 hunger
        // jelek -> -40 mood, -20 health, -30 hunger

        try {
            System.out.println("Sim akan membaca sheet QnA selama " + Angka.secToTime(3*60));
            gm.getClock().moveTime(3 * 60 * 1000);
            
            int chance = Angka.randint(0, 100);
    
            if (chance >= 0 && chance < 20) { // bad
                System.out.println("Wah! Tumben banget ini QnA bagus jawabannya. Sim kamu jadi semangat tubes! +30 mood +10 health -10 hunger");
    
                setMood(getMood()+30);
                setHealth(getHealth()+10);
                setHunger(getHunger()-10);
            } else { // good
                System.out.println("ANJIR! INI QNA KOK NGACO BANGET. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA! -40 mood -20 health -30 hunger");
                
                setMood(getMood()-40);
                setHealth(getHealth()-20);
                setHunger(getHunger()-30);
            }
        } catch (SimIsDeadException e) {
            throw e;
        }
    }
}