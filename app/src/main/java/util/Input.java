package util;

import java.util.Scanner;

public class Input {
    private static Input instance = null;
    private Scanner scan;

    private Input() {
        scan = new Scanner(System.in);
    }

    public static Input getInstance() {
        if (instance == null) {
            instance = new Input();
        }

        return instance;
    }

    public int nextInt() {
        return scan.nextInt();
    }

    public String next() {
        return scan.next();
    }
}
