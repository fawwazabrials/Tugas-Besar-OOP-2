package entity;

import java.util.*;

import map.House;
import map.Room;
import map.Direction;
import item.Food;
import item.Dish;
import item.Ingredients;
import item.Item;
import main.Game;

public class Sim extends Exception implements SimAction {
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
    private String job;

    private long timeLastPoop; //belum pake di method poop
    private long timeLastEat;  //belum pake di method eat
    private long timeLastSleep;
    private long bufferedVisitTime;

    private Thread upgradeHouse;

    // private boolean visiting;

    public Sim(House house, Room currRoom, String name) {
        this.name = name;
        this.simHouse = house;
        this.currRoom = currRoom;
        this.upgradeHouse = null;
        currHouse = simHouse;
        // visiting = false;
        bufferedVisitTime = 0;
        
        alive = true;
        mood = 80;
        hunger = 80;
        health = 80;

        money = 10000;

        x = 5;
        y = 3;
    }

    public String getName() {return name;}
    public House getHouse() {return simHouse;}
    public House getCurrHouse() {return currHouse;}
    public Inventory getSimItems() {return simItems;}
    public Room getRoom() {return currRoom;}
    public void setRoom(Room newRoom) {
        currRoom = newRoom;
    }
    public String getJob() {return job;}
    public void setJob(String job) {this.job = job;}
    public int getMood() {return mood;}
    public void setMood(int newMood) {
        if (mood > 100) mood = 100;
        else mood = newMood;
    }
    public int getHunger() {return hunger;}
    public void setHunger(int newHunger) {
        if (hunger > 100) hunger = 100;
        else hunger = newHunger;
    }
    public int getHealth() {return health;}
    public void setHealth(int newHealth) {
        if (health > 100) health = 100;
        else health = newHealth;
    }
    public int getMoney() {return money;}

    public Thread getUpgradeHouse() {
        return upgradeHouse;
    }

    public void setMoney(int money) {
        this.money+= money;
    }
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
    public void work(int time) {
        /*
        * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
        */
            hunger-= ((10*time)/30000);
            mood-= ((10*time)/30000);
            if(time%4 == 0) {
                if(job == "badut sulap") {
                    money += (15*(time%240000));
                }
                else if(job == "koki") {
                    money += (30*(time%240000));
                }
                else if(job == "polisi") {
                    money += (35*(time%240000));
                }
                else if(job == "programmer") {
                    money += (45*(time%240000));
                }
                else if(job == "dokter") {
                    money += (50*(time%240000));
                }
            }
            Game.moveTime(time);

            updateSim();
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
        * +30 mood, +20 kesehatan / 4 menit
        */
        mood+= (30*time/240000);
        health+= (20*time/240000);
        Game.moveTime(time);

        updateSim();
    }

    public boolean isDead() {
        if (health < 0 || mood < 0 || hunger < 0) return true;
        return false;
    }

    public void killSim() {
        alive = false;
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
        int visittime = (int)(Math.sqrt(Math.pow((this.getHouse().getX() - target.getHouse().getX()), 2) + Math.pow((this.getHouse().getY() - target.getHouse().getY()), 2)));
        int cycle = (int)(visittime / 30);

        // DEBUG: INSTANT MOVE
        // Game.moveTime(0);

        for (int i=0; i<cycle; i++) {
            Game.moveTime(30 * 1000);
            setHunger(getHunger() - 10);
            setMood(getMood() + 10);
        }

        Game.moveTime(visittime%30 * 1000); // sisa waktu
        bufferedVisitTime += visittime%30 * 1000;

        if (bufferedVisitTime > 30) {
            cycle = (int)(bufferedVisitTime / 30);
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
        // new Thread(new Runnable() {
        //     public void run() {
        //         delay(waktu antar);
        //         add inventory
        //     }
        // }).start();
    }

    @Override
    public void sellItem(Item item) {
        
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
}