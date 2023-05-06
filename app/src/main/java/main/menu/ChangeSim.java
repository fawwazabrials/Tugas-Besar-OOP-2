package main.menu;

import java.util.InputMismatchException;

import exception.NoInputException;
import main.Game;
import util.Input;

public class ChangeSim implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int simNum = gm.getWorld().getSims().size();
        boolean deadscreen = gm.getCurrentSim().isDead();

        if (simNum <= 1 && !deadscreen) {
            System.out.println("Kamu hanya memiliki 1 sim! Silahkan buat sim lain untuk dimainkan.");
            scan.enterUntukLanjut();
        } 
        
        else {
            // Print available sims
            System.out.println(String.format(" %s ", "---------------------- SIM YANG ADA ----------------------"));
            System.out.println(String.format("| %-3s | %-50s |", "No", "Sim"));
            System.out.println(String.format(" %s ", "----------------------------------------------------------"));

            for (int i=0; i<gm.getWorld().getSims().size(); i++) {
                if (gm.getWorld().getSims().get(i) == gm.getCurrentSim())
                    System.out.println(String.format("| %-3s | %-50s |", i+1, (gm.getWorld().getSims().get(i).getName() + " (Sim sekarang)")));
                else
                    System.out.println(String.format("| %-3s | %-50s |", i+1, gm.getWorld().getSims().get(i).getName()));
            }

            System.out.println(String.format(" %s ", "----------------------------------------------------------"));

            // Get input
            int input = -999;
            boolean getInput = true;

            while (getInput) {
                try {
                    input = scan.getIntegerInput("\nPilih sim yang ingin dimainkan (dalam angka): ");

                    if (input <=0 || input > simNum) {
                        System.out.println("Masukan angka di dalam batas sim!");
                    }

                    else {
                        if (gm.getWorld().getSims().get(input-1) == gm.getCurrentSim()) {
                            System.out.println("\nTidak bisa mengganti ke sim yang sedang dimainkan!");
                            scan.enterUntukLanjut();
                        } else {
                            gm.changeSim(gm.getWorld().getSims().get(input-1));
                        }

                        getInput = false;
                    }
                }
                catch (NoInputException e) {
                    getInput = false;
                }
                catch (InputMismatchException e) {
                    System.out.println("Masukkan angka!");
                }
            }
        }
    }
}