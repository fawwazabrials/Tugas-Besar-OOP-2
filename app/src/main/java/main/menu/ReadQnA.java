package main.menu;

import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class ReadQnA implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        try {
            System.out.println("Sim sedang membaca sheet QnA tubes...");
            gm.getCurrentSim().readQnA();
            System.out.println("Sim selesai membaca sheet QnA tubes...");
            scan.enterUntukLanjut();
        } catch (SimIsDeadException e) {
            System.out.println("QnA dan tubes terlalu ga jelas... sim kamu mati karena stres.");
        }
    }
}
