package util;

public class Angka {
    public static int stringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -999;
        }
    }

    public static String secToTime(int secs) {
        String time = "";
        int minutes = secs / 60;
        int seconds = secs % 60;

        if (minutes >= 0) time += minutes + " menit";
        if (minutes >= 0 && seconds >= 0) time += " ";
        if (seconds >= 0) time += seconds + " detik";

        return time;
    }
}
