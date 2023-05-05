package main.menu;

import java.util.InputMismatchException;
import java.util.List;

import entity.Sim;
import exception.NoInputException;
import main.Game;
import util.Input;

public class Visit implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        List<Sim> sims = gm.getWorld().getSims();
        int simNum = sims.size();
        int houseNow = 0;

        if (simNum <= 1) {
            System.out.println("\nTidak ada sim lain! Silahkan buat sim terlebih dahulu!");
            scan.enterUntukLanjut();
        } 
        
        else {
            System.out.println("\nSim yang bisa dikunjungi: ");
            for (int i=0; i<simNum; i++) {
                System.out.print((i+1) + ". " + sims.get(i).getName() + " (" + sims.get(i).getHouse().getX() + ", " + sims.get(i).getHouse().getY() + ")");
                if (gm.getCurrentSim().getCurrHouse() == sims.get(i).getHouse()) { // sim sekarang lagi ada dirumah mana
                    System.out.println(" (Lokasi sekarang)");
                    houseNow = i;
                } else {
                    System.out.println("");
                }
            }

            int input;
            boolean getInput = true;

            while (getInput) {
                try {
                    input = scan.getIntegerInput("MASUKKAN SIM YANG INGIN DIKUNJUNGI: ");

                    if (input <= 0 || input > simNum) {
                        System.out.println("Masukkan angka dalam batas sim! Silahkan coba ulang.");
                        scan.enterUntukLanjut();
                    } else if (input == (houseNow+1)) {
                        System.out.println("Sim tidak bisa mengujungi lokasi dia sekarang! Silahkan coba ulang.");
                        scan.enterUntukLanjut();
                    } else {
                        System.out.println("\nSim sedang berjalan untuk berkunjung...");
        
                        gm.getCurrentSim().visit(sims.get(input-1));
                        gm.setCurrentHouse(sims.get(input-1).getName());
                        getInput = false;
        
                        System.out.println("\nSim sudah sampai!");
                        scan.enterUntukLanjut();
                    }
                }
                catch (NoInputException e) {
                    getInput = false;
                }
                catch (InputMismatchException  e) {
                    System.out.println("Masukkan angka!");
                }
            }
        }
    }

}
