package main;

import entity.Sim;
import map.Renderable;
import map.World;

public class Game {
    public Renderable currentView;
    public Sim currentSim;
    public World world;
    public GamePanel gp;
    public long gameTime, time, oldTime;

    public Game(GamePanel gp) {
        this.gp = gp;
        time = System.currentTimeMillis();
        gameTime = 0;

        // Fresh start
        world = World.getInstance(gp);
        world.addHouse(0, 0);
        currentSim = world.getHouses().get(0).getOwner();
        currentView = currentSim.getRoom();
        
    }

    public void changeView(Renderable view) {
        currentView = view;
    }

    public void update() {
        oldTime = time;
        time = System.currentTimeMillis();

        gameTime = (gameTime + time - oldTime) % 720000;
        // System.out.println(gameTime);
    }
}
