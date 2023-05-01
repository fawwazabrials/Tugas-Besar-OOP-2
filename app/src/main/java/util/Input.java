package util;

import java.io.IOException;
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

    public String nextLine() {
        return scan.nextLine();
    }

    public void enterUntukLanjut() {
        System.out.println("\n(Tekan ENTER untuk lanjut)");
        try {
            System.in.read();
            scan.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
