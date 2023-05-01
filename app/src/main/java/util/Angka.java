package util;

public class Angka {
    public static int stringToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -999;
        }
    }
}
