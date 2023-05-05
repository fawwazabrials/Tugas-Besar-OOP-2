package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Sleep implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int input;
        boolean getInput = true;

        while (getInput) {
            try {
                input = scan.getIntegerInput("MASUKKAN WAKTU (dalam kelipatan 240 detik): ");

                if (input % 240 == 0 && input != 0) {
                    System.out.println("\nSim sedang tidur...");
                    gm.getCurrentSim().sleep(input);
                    System.out.println("\nSim sudah bangun!");
                    getInput = false;
                    scan.enterUntukLanjut();
                } else {
                    System.out.println("Masukan waktu dalam kelipatan 240 detik!");
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
