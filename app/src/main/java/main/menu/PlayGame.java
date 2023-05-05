package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class PlayGame implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int input;
        boolean getInput = true;

        while (getInput) {
            try {
                input = scan.getIntegerInput("MASUKKAN WAKTU (dalam kelipatan 120 detik): ");

                if (input % 120 == 0 && input != 0) {
                    System.out.println("\nSim sedang bermain game...");
                    gm.getCurrentSim().playGame(input);
                    System.out.println("\nSim sudah selesai nge-game!");
                    getInput = false;
                    scan.enterUntukLanjut();
                } else {
                    System.out.println("Masukan waktu dalam kelipatan 120 detik!");
                }
            }
            catch (NoInputException e) {
                getInput = false;
            }
            catch (InputMismatchException  e) {
                System.out.println("Masukkan angka!");
            }
            catch (SimIsDeadException e) {
                
            }
        }
    }
    
}
