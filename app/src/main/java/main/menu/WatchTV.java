package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class WatchTV implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int input;
        boolean getInput = true;

        while (getInput) {
            try {
                input = scan.getIntegerInput("MASUKKAN WAKTU (dalam kelipatan 60 detik): ");

                if (input % 60 == 0 && input >= 0) {
                    
                    gm.getCurrentSim().watchTV(input);
                    System.out.println("\nSim sudah selesai nonton TV!");
                    getInput = false;
                    scan.enterUntukLanjut();
                } else {
                    System.out.println("Masukan waktu dalam kelipatan 60 detik!");
                }
            }
            catch (NoInputException e) {
                getInput = false;
            }
            catch (InputMismatchException  e) {
                System.out.println("Masukkan angka!");
            }
            catch (SimIsDeadException e) {
                System.out.println(e.getMessage());
                getInput = false;
            }
        }
    }
}
