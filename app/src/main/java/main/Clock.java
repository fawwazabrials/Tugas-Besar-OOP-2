package main;

public class Clock {
    private int gameTime, day;

    public Clock() {
        gameTime = 0;
        day = 1;
    }

    public int getGameTime() {return gameTime;}
    public int getDay() {return day;}
    
    public void forwardTime(int time) {
        gameTime = gameTime+time;
    }
    
    public void moveTime(int time) {
        int secs = time / 1000;

        for (int i=0; i<secs; i++) {
            if (!Game.getCurrentSim().isDead()) {
                try {
                    if (!Cheat.isSkiptime()) {
                        Thread.sleep(1000); // 1 second
                    } forwardTime(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }    
            } 
        }
    }
}
