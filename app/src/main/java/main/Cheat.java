package main;

public class Cheat {
    private static boolean skiptime, fastbuild, fastshop, addinfinitesim;

    public static boolean isAddinfinitesim() {return addinfinitesim;}
    public static boolean isFastshop() {return fastshop;}
    public static boolean isFastbuild() {return fastbuild;}
    public static boolean isSkiptime() {return skiptime;}

    public static void resetAllCheats() {
        skiptime = false;
        fastbuild = false;
        fastshop = false;
        addinfinitesim = false;
    }

    public static void cheatOptions(String cheat) {
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
                Game.getCurrentSim().killSim();
                break;

            case "addinfinitesim":
                addinfinitesim = !addinfinitesim;
                break;
        }
    }

    public static void cheatOptions(String cheat, int val) {
        // value cheats
        switch (cheat) {
            case "money":
                Game.getCurrentSim().setMoney(val);
                break;

            case "mood":
                Game.getCurrentSim().setMood(val);
                break;

            case "health":
                Game.getCurrentSim().setHealth(val);
                break;

            case "hunger":
                Game.getCurrentSim().setHunger(val);
                break;

            case "forwardtime":
                Game.getClock().moveTime(val);
                break;
        }
    }
}
