package main;

import java.util.Scanner;

import util.ClearScreen;

public class TitleScreen {
    Scanner scan = new Scanner(System.in);

    public TitleScreen() {
        show();
    }

    private void show() {
        System.out.println("1. Start game");
        System.out.println("2. Quit");

        getInput();
    }

    private void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.next();

        if (input.equals("1")) {
            scan.close();
            // pass, quit from method
        }
        else if (input.equals("2")) {
            scan.close();
            System.exit(0);
        }
        else {
            ClearScreen.clear();
            show();
        }
    }
}
