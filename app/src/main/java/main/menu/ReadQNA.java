package main.menu;

import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class ReadQNA implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        try {

            gm.getCurrentSim().readQNA();
            System.out.println("Sim selesai membaca sheet QnA tubes!");
            scan.enterUntukLanjut();
            
        } catch (SimIsDeadException e) {
            System.out.println("QnA dan tubes terlalu ga jelas... sim kamu mati karena stres.");
        }
    }
}
