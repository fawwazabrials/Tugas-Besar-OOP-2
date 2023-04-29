package main;

import map.Renderable;

public class Game {
    public Renderable currentView;
    public long gameTime, time, oldTime;

    public Game() {
        time = System.currentTimeMillis();
        gameTime = 0;
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
