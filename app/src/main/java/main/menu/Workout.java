package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Workout implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {

        int input;
        boolean getInput = true;
        while(getInput) {
            try {
                input = scan.getIntegerInput("\nMASUKKAN WAKTU (dalam kelipatan 20 detik): ");

                if (input % 20 == 0 && input <= 0) {
                    
                    gm.getCurrentSim().workout(input);
                    System.out.println("\nSim selesai berolahraga!");
                    getInput = false;
                    scan.enterUntukLanjut();
                } else {
                    System.out.println("Masukan waktu dalam kelipatan 20 detik!");
                }

            } catch (NoInputException e) {
                getInput = false;
            }
            catch (InputMismatchException  e) {
                System.out.println("Masukkan angka!");
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                getInput = false;
                scan.enterUntukLanjut();
            }
            catch (SimIsDeadException e) {
                System.out.println(e.getMessage());
                getInput = false;
            }
        }
    }

}
