package entity;

import java.util.*;

import map.House;
import map.Room;
import util.Angka;
import map.Direction;
import item.Food;
import item.Dish;
import item.Ingredients;
import item.Item;
import main.Game;

public class Sim extends Exception implements SimAction, Runnable {
    boolean alive;

    private int x, y;

    private String name;

    private House simHouse, currHouse;
    private Room currRoom;

    private Inventory simItems;
    private int mood;
    private int hunger;
    private int health;
    private int money;
    private Job job;

    private long timeLastPoop; //belum pake di method poop
    private long timeLastEat;  //belum pake di method eat
    private volatile long timeLastSleep;
    private volatile int dayLastSleep;

    private long bufferedVisitTime, bufferedWorkTime;
    private int timeChangedJob, totalJobTimeWorked;

    private Thread upgradeHouse, trackUpdates;

    // private boolean visiting;

    public Sim(House house, Room currRoom, String name) {
        this.name = name;
        this.simHouse = house;
        this.currRoom = currRoom;
        this.upgradeHouse = null;
        currHouse = simHouse;
        
        job = Job.createRandomJob();
        simItems = new Inventory();

        bufferedVisitTime = 0;
        bufferedWorkTime = 0;
        timeChangedJob = -999;
        totalJobTimeWorked = 0;

        resetTimeLastSleep(); dayLastSleep = (int)Game.getDay();
        
        alive = true;
        mood = 80;
        hunger = 80;
        health = 80;

        money = 10000;

        x = 5;
        y = 3;

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
        timeChangedJob = (int)Game.getDay();
        totalJobTimeWorked = 0;
    }
    public int getMood() {return mood;}
    public synchronized void setMood(int newMood) {
        mood = newMood;
        if (mood > 100) mood = 100;
    }
    public int getHunger() {return hunger;}
    public synchronized void setHunger(int newHunger) {
        hunger = newHunger;
        if (hunger > 100) hunger = 100;
    }
    public int getHealth() {return health;}
    public synchronized void setHealth(int newHealth) {
        health = newHealth;
        if (health > 100) health = 100;
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

    public void goToObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void upgradeHouse(String roomName, Room target, Direction direction) {
        if (upgradeHouse == null) {
            money -= 1500;
            upgradeHouse = new Thread(new Runnable() {
                long timeLastUpgrade = Game.getTime();
                public void run(){
                    while (upgradeHouse != null) {
                        try {
                            if (Game.getTime() - timeLastUpgrade >= 18 * 60) {
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

    @Override
    public void work(int time) throws IllegalArgumentException {
        /*
        * PREREQUISITE : time sudah pasti kelipatan 4 menit / 120 detik
        * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
        */

        if (Game.getTime() - timeChangedJob < 12*60) {
            System.out.println(timeChangedJob);
            System.out.println(Game.getTime() - timeChangedJob);

            throw new IllegalArgumentException(("Sim harus menunggu 1 hari setelah pergantian kerja untuk bekerja! Sisa waktu tunggu " + Angka.secToTime(12*60-timeChangedJob)));
        }
        
        // else
        int cycle = time / 30;

        System.out.println("\nSim akan bekerja selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            Game.moveTime(30 * 1000); // waktu jalan 30dtk

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

    @Override
    public void workout(int time) {
        /*
        * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
        */
        int cycle = time / 20000;


        health+= (5*time/20000);
        hunger-= (5*time/20000);
        mood+= (10*time/20000);
        Game.moveTime(time);

        updateSim();
    }

    @Override
    public void sleep(int time) {
        /*
        * PREREQUISITE: time sudah pasti kelipatan 4 menit / 240 detik
        * +30 mood, +20 kesehatan / 4 menit
        */
        int cycle = time / (60 * 4);

        System.out.println("Sim akan tidur selama " + Angka.secToTime(time));

        for (int i=0; i<cycle; i++) {
            Game.moveTime(60 * 4 * 1000);

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

    @Override
    public void eat(int time, Food food){
        /*
        * +X kekenyangan (X sesuai makanan) / siklus makan(30 detik); Makanan yang dimakan akan hilang dari inventory
        */
        if(!simItems.getItems("food").containsKey(food)){
            throw new IllegalArgumentException("No food in inventory.");
        }
        for(Map.Entry<Item, Integer> e : simItems.getItems("food").entrySet()){
            if(e.getKey().getName().equals(food.getName())){
                hunger += (food.getHungerPoint()*(time%30000));
            }
        }
        Game.moveTime(time);
    }
    @Override
    public void cook(Food dish) {
        switch(dish.getName()){
            case "nasi ayam":
            if(simItems.checkItemAvailable("nasi",1) && simItems.checkItemAvailable("ayam",1)){
                simItems.removeItem(simItems.getItemsByName("nasi"));
                simItems.removeItem(simItems.getItemsByName("ayam"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("Not enough ingredients in inventory.");
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
                throw new IllegalArgumentException("Not enough ingredients in inventory.");
            }
            break;
            case "susu kacang":
            if(simItems.checkItemAvailable("susu",1) && simItems.checkItemAvailable("kacang",1)){
                simItems.removeItem(simItems.getItemsByName("susu"));
                simItems.removeItem(simItems.getItemsByName("kacang"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("Not enough ingredients in inventory.");
            }
            break;
            case "tumis sayur":
            if(simItems.checkItemAvailable("wortel",1) && simItems.checkItemAvailable("bayam",1)){
                simItems.removeItem(simItems.getItemsByName("wortel"));
                simItems.removeItem(simItems.getItemsByName("bayam"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("Not enough ingredients in inventory.");
            }
            break;
            case "bistik":
            if(simItems.checkItemAvailable("kentang",1) && simItems.checkItemAvailable("sapi",1)){
                simItems.removeItem(simItems.getItemsByName("kentang"));
                simItems.removeItem(simItems.getItemsByName("sapi"));
                simItems.addItem(dish);
            }
            else{
                throw new IllegalArgumentException("Not enough ingredients in inventory.");
            }
            break;
            default: throw new IllegalArgumentException("No such dish to cook.");
        }
        mood += 10;
        Game.moveTime((int) 1.5*dish.getHungerPoint());
    }

    @Override
    public void visit(Sim target) {
        int visittime = (int)(Math.sqrt(Math.pow((this.getCurrHouse().getX() - target.getHouse().getX()), 2) + Math.pow((this.getCurrHouse().getY() - target.getHouse().getY()), 2)));
        int cycle = (int)(visittime / 30);

        // DEBUG: INSTANT MOVE
        // Game.moveTime(0);

        System.out.println("\nSim akan berjalan selama " + visittime + " detik!");

        for (int i=0; i<cycle; i++) {
            Game.moveTime(30 * 1000);
            System.out.println("\nSim sudah berjalan selama 30 detik, waktu tersisa: " + Angka.secToTime(visittime-30*i));
            System.out.println("Keren ya ternyata pemandangannya! -10 hunger +10 mood");
            setHunger(getHunger() - 10);
            setMood(getMood() + 10);
        }

        Game.moveTime(visittime%30 * 1000); // sisa waktu
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

    @Override
    public void poop(int time) {
        hunger-= (20*time/10000);
        mood+= (10*time/10000);
        Game.moveTime(time);

        updateSim();
    }

    @Override
    public void buyItem(Item item) {
        if(item.getPriceValue() > money){
            throw new IllegalArgumentException("Not enough money");
        }
        money -= item.getPriceValue();
        simItems.addItem(item);
    }

    @Override
    public void sellItem(Item item) {
        if(!simItems.checkItemAvailable(item.getName(), 1)){
            throw new IllegalArgumentException("Not enough item to sell");
        }
        simItems.removeItem(item);
        money += item.getPriceValue();
    }

    @Override
    public void move(Room target) {
        currRoom = target;
    }

    @Override
    public void stargaze(int time) {
        mood += (20*time/60000);
        hunger -= (15*time/60000);
        Game.moveTime(time);

        updateSim();
    }

    @Override
    public void read(int time) {
        mood += (20*time/60000);
        hunger -= (15*time/60000);
        Game.moveTime(time);

        updateSim();
    }

    @Override
    public void run() {
        while (trackUpdates != null) {
            // System.out.println(timeLastSleep);// track sleep
            if ((Game.getTime() - timeLastSleep) >= 10*60) {
                // System.out.println("HIT!");
                resetTimeLastSleep();
    
                setHealth(getHealth() - 5);
                setMood(getMood() - 5);
            }
    
            if (dayLastSleep < Game.getDay()) {
                resetTimeLastSleep();
                dayLastSleep = (int)Game.getDay();
            }
        }
    }

    public void resetTimeLastSleep() {
        timeLastSleep = Game.getTime();
    }
}