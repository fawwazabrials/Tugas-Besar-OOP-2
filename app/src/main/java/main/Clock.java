package main;

import exception.SimIsDeadException;

public class Clock {
    private int gameTime, day;
    private Game gm;

    public Clock(Game gm) {
        this.gm = gm;

        gameTime = 0;
        day = 1;
    }

    public int getGameTime() {return gameTime;}
    public int getDay() {return day;}
    
    public void forwardTime(int time) {
        gameTime = gameTime+time;
    }
    
    public void moveTime(int time) throws SimIsDeadException {
        int secs = time / 1000;

        for (int i=0; i<secs; i++) {
            if (!gm.getCurrentSim().isDead()) {
                try {
                    if (!gm.getCheat().isSkiptime()) {
                        Thread.sleep(1000); // 1 second
                    } forwardTime(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }    
            } else {
                throw new SimIsDeadException();
            }
        }
    }
}
