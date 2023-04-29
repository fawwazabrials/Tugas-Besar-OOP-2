package entity;

import java.util.List;

import main.GamePanel;
import map.House;
import map.Room;
import item.Food;
import item.Item;


public class Sim implements SimAction{
    public int x, y, speed; // Game stuff
    private House house;
    private List<Item> simItems;
    private Room currRoom;
    private int mood;
    private int hunger;
    private int health;
    private int money;
    private String job;

    GamePanel gp;

    public Sim(GamePanel gp, House house, List<Item> simItems, Room currRoom) {
        this.house = house;
        this.simItems = simItems;
        this.currRoom = currRoom;
        
        mood = 100;
        hunger = 100;
        health = 100;
    }

    /* INI ABIL YG NGURUS, JANGAN DIAPUS! */
    public void update() {

    }

    public void draw() {

    }

    public House getHouse() {
        return house;
    }

    public List<Item> getSimItems() {
        return simItems;
    }

    public Room getRoom() {
        return currRoom;
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

    @Override
    public void work(int time) {
        /*
        * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
        */
        hunger-= ((10*time)/30);
        mood-= ((10*time)/30);
        if(job == "Badut Sulap") {
            money += (15*time/4);
        }
        else if(job == "Koki") {
            money += (30*time/4);
        }
        else if(job == "Polisi") {
            money += (35*time/4);
        }
        else if(job == "Programmer") {
            money += (45*time/4);
        }
        else if(job == "Dokter") {
            money += (50*time/4);
        }
    }

    @Override
    public void workout(int time) {
        /*
        * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
        */
        health+= (5*time/20);
        hunger-= (5*time/20);
        mood+= (10*time/20);
    }

    @Override
    public void sleep(int time) {
        /*
        * +30 mood, +20 kesehatan / 4 menit
        */
        mood+= (30*time/(4));
        health+= (20*time/(4));
    }

    @Override
    public void eat(int time, Food food) {
        /*
        * +X kekenyangan (X sesuai makanan) / siklus makan(30 detik); Makanan yang dimakan akan hilang dari inventory
        */
        
    }

    @Override
    public void cook(int time, Food dish) {
        
    }

    @Override
    public void visit(int time, Sim target) {
        /*
        * +10 mood, -10 kekenyangan / 30 detik
        */ 
        mood += (10*time/30);
        hunger -= (10*time/30);
    }

    @Override
    public void poop(int time) {
        /*
        * -20 kekenyangan, +10 mood / 1 siklus (10 detik)
        */
        hunger-= (20*time/10);
        mood+= (10*time/10);
    }

    @Override
    public void buyItem(Item item) {
        /* 
        * Membeli Item dari toko, jika Sim tidak memiliki cukup uang maka method akan mengembalikan error 
        */
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
        mood += (20*time);
        hunger -= (15*time);
    }
    /*
     * +20 mood, -15 kekenyangan / 1 menit
     */

    @Override
    public void read(int time) {
        mood += (20*time);
        hunger -= (15*time);
    }
    /*
     * +20 mood, -15 kekenyangan / 1 menit
     */
}
