package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import main.Game;
import util.Input;

public class Gamble implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int input;
        boolean getInput = true;

        while (getInput) {
            try {
                input = scan.getIntegerInput("Masukkan uang yang ingin dijudikan: ");

                gm.getCurrentSim().gamble(input);
                getInput = false;
                scan.enterUntukLanjut();
            }
            catch (NoInputException e) {
                getInput = false;
            }
            catch (InputMismatchException  e) {
                System.out.println("Masukkan angka!");
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}