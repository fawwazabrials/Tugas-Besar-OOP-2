package main;

import exception.SimIsDeadException;

public class Cheat {
    Game gm;

    private boolean skiptime, fastbuild, fastshop, addinfinitesim;

    public boolean isAddinfinitesim() {return addinfinitesim;}
    public boolean isFastshop() {return fastshop;}
    public boolean isFastbuild() {return fastbuild;}
    public boolean isSkiptime() {return skiptime;}

    public Cheat(Game gm) {
        this.gm = gm;
        resetAllCheats();
    }

    public void resetAllCheats() {
        skiptime = false;
        fastbuild = false;
        fastshop = false;
        addinfinitesim = false;
    }

    public void cheatOptions(String cheat) {
        // toggle cheat

        switch (cheat) {
            case "skiptime":
                skiptime = !skiptime;
                break;

            case "fastbuild":
                fastbuild = !fastbuild; // TODO: implement this cheat
                break;

            case "fastshop":
                fastshop = !fastshop; // TODO: implement this cheat
                break;

            case "killcurrentsim":
                gm.getCurrentSim().killSim();
                break;

            case "addinfinitesim":
                addinfinitesim = !addinfinitesim;
                break;
        }
    }

    public void cheatOptions(String cheat, int val) {
        // value cheats
        switch (cheat) {
            case "money":
                gm.getCurrentSim().setMoney(val);
                break;

            case "mood":
                gm.getCurrentSim().setMood(val);
                break;

            case "health":
                gm.getCurrentSim().setHealth(val);
                break;

            case "hunger":
                gm.getCurrentSim().setHunger(val);
                break;

            case "forwardtime":
                try {
                    boolean old = skiptime;
                    skiptime = true;
                    gm.getClock().moveTime(val*1000);
                    skiptime = old;
                } catch (SimIsDeadException e) {
                    System.out.println("Damn ternyata setelah majuin waktu Sim kamu mati!");
                }
                break;
        }
    }
}
