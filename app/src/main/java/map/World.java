package map;
import java.util.*;

import entity.Sim;
import main.Game;

public class World implements WorldAction{
    private static World instance = null;
    private Game gm;
    private List<Sim> sims = new ArrayList<>();
    private Sim[][] worldMap;

    // Private Constructor
    public World(Game gm) {
        this.gm = gm;

        int width = 64;
        int length = 64;
        worldMap = new Sim[width][length];
    }

    public static World getInstance(Game gm) {
        if (instance == null) {
            instance = new World(gm);
        }
        return instance;
    }

    // Getter Method
    public List<Sim> getSims() {return sims;}

    public void addHouse(int x, int y, String simName) {
        if (worldMap[x][y] != null) {
            throw new IllegalArgumentException("House already exists");
        }
        House newHouse = new House(x, y);
        Sim newSim = new Sim(gm, newHouse, newHouse.getRooms().get(0), simName);
        sims.add(newSim);
        worldMap[x][y] = newSim;
    }

    public void removeHouse(int x, int y) {
        if (worldMap[x][y] == null) {
            throw new IllegalArgumentException("House doesn't exist");
        }
        Sim removedSim = worldMap[x][y];
        sims.remove(removedSim);
        worldMap[x][y] = null;
    }
}