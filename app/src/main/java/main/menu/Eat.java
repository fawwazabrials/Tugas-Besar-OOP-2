package main.menu;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Eat implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        gm.getCurrentSim().getSimItems().print("food");

        if (gm.getCurrentSim().getSimItems().getItems("food").size() <= 0) {
            scan.enterUntukLanjut();
        } 
        
        else {
            try {
                String input = scan.getInput("Masukkan nama makanan yang ingin dimakan: ");
                gm.getCurrentSim().eat(input);
                scan.enterUntukLanjut();

            } catch (NoInputException e) {
                //ignore
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            } catch (SimIsDeadException e) {
                System.out.println(e.getMessage());
            }

        }

    }
    
}
