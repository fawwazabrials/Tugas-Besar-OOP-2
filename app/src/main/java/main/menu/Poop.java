package main.menu;

import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Poop implements Option {
    
    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        // 1 siklus = 10 detik

        try {
            gm.getCurrentSim().poop();
            System.out.println("Sim selesai buang air!");
            scan.enterUntukLanjut();
        } catch (SimIsDeadException e) {
            System.out.println(e.getMessage());
        } 
    }
    
}
