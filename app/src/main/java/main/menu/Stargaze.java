package main.menu;

import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Stargaze implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        try {
            gm.getCurrentSim().stargaze();
            scan.enterUntukLanjut();
        } catch (SimIsDeadException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
