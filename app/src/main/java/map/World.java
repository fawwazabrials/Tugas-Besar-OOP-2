package map;
import java.util.*;

public class World {
    private static World instance = null;
    private List<House> houses = new ArrayList<>();
    private House[][] worldMap;

    // Private Constructor
    private World() {
        int width = 64;
        int length = 64;
        worldMap = new House[width][length];
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    // Getter Method
    public List<House> getHouses() {return houses;}

    public void addHouse(int x, int y) {
        if (worldMap[x][y] != null) {
            throw new IllegalArgumentException("House already exists");
        }
        House newHouse = new House(x, y);
        houses.add(newHouse);
        worldMap[x][y] = newHouse;
    }

    public void removeHouse(int x, int y) {
        if (worldMap[x][y] == null) {
            throw new IllegalArgumentException("House doesn't exist");
        }
        House removedHouse = worldMap[x][y];
        houses.remove(removedHouse);
        worldMap[x][y] = null;
    }
}