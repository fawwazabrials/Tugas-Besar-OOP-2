package util;

import java.util.concurrent.ThreadLocalRandom;

public class Angka {
    public static int stringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    public static String secToTime(int secs) {
        int hours = secs / 3600;
        int minutes = secs / 60;
        int seconds = secs % 60;

        String hh = "";
        String mm = "";
        String ss = "";

        if (hours < 10) hh += "0";
        hh += hours;
        
        if (minutes < 10) mm += "0";
        mm += minutes;

        if (seconds < 10) ss += "0";
        ss += seconds;

        return String.format("%s:%s:%s", hh, mm, ss);
    }

    public static int randint(int lower, int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper + 1);
    }
}
