package main.menu;

import main.Game;
import util.Angka;
import util.Input;

public class ChangeSim implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int simNum = gm.getWorld().getSims().size();
        boolean deadscreen = gm.getCurrentSim().isDead();

        if (simNum <= 1 && !deadscreen) {
            System.out.println("\nKamu hanya memiliki 1 sim! Silahkan buat sim lain untuk dimainkan.");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nPilih sim mana yang ingin dimainkan: ");
            int cnt = 1;
            for (int i=0; i<simNum; i++) {
                if (gm.getWorld().getSims().get(i) == gm.getCurrentSim()) { // Bukan sim yang sekarang lagi dimainin
                    System.out.println(cnt + ". " + gm.getWorld().getSims().get(i).getName() + " (Sim sekarang)");
                } else {
                    System.out.println(cnt + ". " + gm.getWorld().getSims().get(i).getName());
                }
                cnt++;
            }

            int input = -999;
            while (input <= 0) {
                System.out.print("PILIH SIM: ");
                input = Angka.stringToInt(scan.nextLine());

                if (input <= 0 || input > simNum) {
                    System.out.println("\nMasukan angka di dalam batas sim!");
                    input = -1;
                } else {
                    if (gm.getWorld().getSims().get(input-1) == gm.getCurrentSim()) {
                        System.out.println("\nTidak bisa mengganti ke sim yang sedang dimainkan!");
                        scan.enterUntukLanjut();
                    } else {
                        gm.changeSim(gm.getWorld().getSims().get(input-1));
                    }
                }
            }
        }
    }

}