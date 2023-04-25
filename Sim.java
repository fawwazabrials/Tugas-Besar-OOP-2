

public class Sim {
    private House house;
    private List<item> simItems;
    private Room currRoom;
    private int mood;
    private int hunger;
    private int health;

    public Sim(House house, List<item> simItems, Room currRoom, int mood, int hunger, int health) {
        this.house = house;
        this.simItems = simItems;
        this.currRoom = currRoom;
        this.mood = mood;
        this.hunger = hunger;
        this.health = health;
    }

    public House getHouse() {
        return house;
    }

    public List<item> getSimItems() {
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
}

