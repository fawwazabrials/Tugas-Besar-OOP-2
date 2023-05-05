package main.menu;

import main.Game;
import util.Input;

public class Poop implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        // 1 siklus = 10 detik
        System.out.println("\nSim sedang buang air...");
        gm.getCurrentSim().poop(10000); // 10 DETIK
        System.out.println("\nSim selesai buang air!");
        scan.enterUntukLanjut();
    }
    
}
