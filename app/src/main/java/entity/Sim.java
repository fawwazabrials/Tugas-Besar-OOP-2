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

    public int getMood() {
        return mood;
    }

    public int getHunger() {
        return hunger;
    }
    
    public int getHealth() {
        return health;
    }

    @Override
    public void work(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'work'");
    }

    @Override
    public void workout(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'workout'");
    }

    @Override
    public void sleep(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sleep'");
    }

    @Override
    public void eat(int time, Food food) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eat'");
    }

    @Override
    public void cook(int time, Food dish) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cook'");
    }

    @Override
    public void visit(int time, Sim target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void poop(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'poop'");
    }

    @Override
    public void buyItem(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buyItem'");
    }

    @Override
    public void sellItem(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sellItem'");
    }

    @Override
    public void move(Room target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void stargaze(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stargaze'");
    }

    @Override
    public void read(int time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }
}
