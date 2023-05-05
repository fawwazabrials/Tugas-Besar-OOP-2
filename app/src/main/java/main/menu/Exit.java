package main.menu;

import exception.NoInputException;
import main.Game;
import util.Input;

public class Exit implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        String input = "";

        try {
            input = scan.getInput("\nApakah anda yakin ingin keluar dari game? (Y/N) ");
        } catch (NoInputException e) {

        }

        if (input.equals("Y")) {
            System.exit(0);
        }
    }
    
}
