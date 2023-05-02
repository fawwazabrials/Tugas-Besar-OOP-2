package main;

import java.util.Scanner;

import util.ClearScreen;
import util.Input;

public class TitleScreen {
    Input scan = Input.getInstance();

    public TitleScreen() {
        show();
    }

    private void show() {
        System.out.println("1. Start game");
        System.out.println("2. Help");
        System.out.println("3. Exit");

        getInput();
    }

    private void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("1")) {
            // pass, quit from method
        }
        else if (input.equals("2")) {
            // TODO: HELP!
        }
        else if (input.equals("3")) {
            System.exit(0);
        }
        else {
            ClearScreen.clear();
            show();
        }
    }
}
