package entity;

import java.util.*;

import map.House;
import map.Room;
import item.Food;
import item.Dish;
import item.Ingredients;
import item.Item;
import main.Game;

public class Sim extends Exception implements SimAction, Runnable {
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
    private long timeLastUpdate;
    Thread simThread;

    public Sim(House house, Room currRoom, String name) {
        this.name = name;
        this.simHouse = house;
        this.currRoom = currRoom;
        
        mood = 80;
        hunger = 80;
        health = 80;

        money = 100;

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
    }

    @Override
    public void workout(int time) {
        /*
        * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
        */
        health+= (5*time/20000);
        hunger-= (5*time/20000);
        mood+= (10*time/20000);
        Game.moveTime(time);
    }

    @Override
    public void sleep(int time) {
        /*
        * +30 mood, +20 kesehatan / 4 menit
        */
        mood+= (30*time/240000);
        health+= (20*time/240000);
        Game.moveTime(time);
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
    public void visit(int time, Sim target) {
        double visittime;
        visittime = Math.sqrt(Math.pow((x-target.x), 2) + Math.pow((y - target.y), 2));
        int totaltime = (int) visittime + time;
        Game.moveTime(totaltime);
    }

    @Override
    public void poop(int time) {
        hunger-= (20*time/10000);
        mood+= (10*time/10000);
        Game.moveTime(time);
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
    }

    @Override
    public void read(int time) {
        mood += (20*time/60000);
        hunger -= (15*time/60000);
        Game.moveTime(time);
    }

    @Override
    public void run() {
        while (simThread != null) {
            if (Game.getTime() > timeLastUpdate) {
                long delta = Game.getTime() - timeLastUpdate;
                timeLastUpdate += delta;
                timeLastEat += delta;
                timeLastPoop += delta;
                timeLastSleep += delta;
                if (timeLastSleep > 240000) {
                    health -= delta;
                }
                if (health == 0) simThread = null;
            }
        }
    }
}