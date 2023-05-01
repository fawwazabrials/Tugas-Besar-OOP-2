package entity;

import java.util.List;
import java.util.ArrayList;

import map.House;
import map.Room;
import item.Food;
import item.Item;
import main.Game;


public class Sim extends Exception implements SimAction, Runnable {
    private int x, y;

    private String name;
    private House house;
    private List<Item> simItems;
    private Room currRoom;
    private int mood;
    private int hunger;
    private int health;
    private int money;
    private String job;

    private long timeLastPoop;
    private long timeLastEat;
    private long timeLastSleep;
    private long timeLastUpdate;
    Thread simThread;

    public Sim(House house, Room currRoom, String name) {
        this.name = name;
        this.house = house;
        this.currRoom = currRoom;
        
        mood = 80;
        hunger = 80;
        health = 80;

        money = 100;

        x = 5;
        y = 3;
    }

    public String getName() {return name;}

    public House getHouse() {
        return house;
    }

    public List<Item> getSimItems() {
        return simItems;
    }

    public Room getRoom() {
        return currRoom;
    }

    public void setRoom(Room newRoom) {
        currRoom = newRoom;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMood() {
        return mood;
    }

    public int getHunger() {
        return hunger;
    }
    
    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
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

    @Override
    public void work(int time) {
        /*
        * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
        */
        if(time%120 == 0) {
            if(time > 240000) {
                hunger-= ((10*time)/30000);
                mood-= ((10*time)/30000);

                if(job == "Badut Sulap") {
                    money += (15*time/240000);
                }
                else if(job == "Koki") {
                    money += (30*time/240000);
                }
                else if(job == "Polisi") {
                    money += (35*time/240000);
                }
                else if(job == "Programmer") {
                    money += (45*time/240000);
                }
                else if(job == "Dokter") {
                    money += (50*time/240000);
                }
                Game.moveTime(time);
            }
            
        }
        // else {
        //     throw new Exception("Please try again.");
        // }
    }

    @Override
    public void workout(int time) {
        /*
        * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
        */
        if(time%20 == 0) {
            health+= (5*time/20000);
            hunger-= (5*time/20000);
            mood+= (10*time/20000);
            Game.moveTime(time);
        }
        // else {
        //     throw new Exception("Please try again.");
        // }
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
    public void eat(int time, Food food) {
        /*
        * +X kekenyangan (X sesuai makanan) / siklus makan(30 detik); Makanan yang dimakan akan hilang dari inventory
        */
        List<Item> foodItems = new ArrayList<Item>();
        for(Item e : simItems) {
            if (e.getCategory().equals("Food")) {
                foodItems.add(e);
            }
        }
        if(foodItems.contains(food)) {
            switch(food.getName()) {
                case "Nasi" :
                    hunger+= (5*time/30000);
                    simItems.remove("Nasi");
                case "Kentang" :
                    hunger+= (4*time/30000);
                    simItems.remove("Kentang");
                case "Ayam" : 
                    hunger+= (8*time/30000);
                    simItems.remove("Ayam");
                case "Sapi" : 
                    hunger+= (15*time/30000);
                    simItems.remove("Sapi");
                case "Wortel" : 
                    hunger+= (2*time/30000);
                    simItems.remove("Wortel");
                case "Bayam" : 
                    hunger+= (2*time/30000);
                    simItems.remove("Bayam");
                case "Kacang" : 
                    hunger+= (2*time/30000);
                    simItems.remove("Kacang");
                case "Susu" : 
                    hunger+= (1*time/30000);
                    simItems.remove("Susu");
                case "Nasi Ayam" :
                    hunger+= (16*time/30000);
                    simItems.remove("Nasi Ayam");
                case "Nasi Kari" :
                    hunger+= (30*time/30000);
                    simItems.remove("Nasi Kari");
                case "Susu Kacang" : 
                    hunger+= (5*time/30000);
                    simItems.remove("Susu Kacang");
                case "Tumis Sayur" :
                    hunger+= (5*time/30000);
                    simItems.remove("Tumis Sayur");
                case "Bistik" : 
                    hunger+= (22*time/30000);
                    simItems.remove("Bistik");
            }
            Game.moveTime(time);
        }
        // else {
        //     throw new Exception("Not in inventory!");
        // }
    }
    @Override
    public void cook(int time, Food dish) {

        Game.moveTime(time);
    }

    @Override
    public void visit(int time, Sim target) {
        
        Game.moveTime(time);
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
