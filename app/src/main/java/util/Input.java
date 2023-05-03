package util;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import exception.NoInputException;


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
        try {
            while (System.in.available() > 0) {
                scan.next();
            }
        } catch (IOException e) {
    
        }

        return scan.nextLine();
    }

    public void enterUntukLanjut() {
        System.out.println("\n(Tekan ENTER untuk lanjut)");


        nextLine();
    }

    public int getIntegerInput(String message) throws NoInputException, InputMismatchException {
        try {
            String input = getInput(message);
            int intInput = Angka.stringToInt(input);

            if (intInput == -999) {
                throw new InputMismatchException();
            } else {
                return intInput;
            }
        } catch (NoInputException e) {
            throw(e);
        }
    }

    public String getInput(String message) throws NoInputException {
        System.out.println(message);

        String input = nextLine();
        if (input.equals("\n")) {
            throw new NoInputException();
        } else {
            return input;
        }
    }
}
