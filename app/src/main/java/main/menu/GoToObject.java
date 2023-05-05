package main.menu;

import java.util.InputMismatchException;
import java.util.List;

import exception.NoInputException;
import item.Furniture;
import main.Game;
import util.Input;

public class GoToObject implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        List<Furniture> furnitures = gm.getCurrentSim().getRoom().getFurnitures();
        
        if (furnitures.size() == 0) {
            System.out.println("\nTidak ada objek di dalam ruangan!");
            scan.enterUntukLanjut();
        } else {
            (new ListObject(false)).execute(gm);

            int input = -999;
            boolean getInput = true;

            while (getInput) {
                try {
                    input = scan.getIntegerInput("Masukkan angka objek yang dituju: ");

                    if (input <= 0 || input > furnitures.size()) {
                        System.out.println("Masukkan angka dalam batas objek!");
                    } else {
                        gm.getCurrentSim().setCoordinates(furnitures.get(input-1).getY(), furnitures.get(input-1).getX());
                        gm.setOverlapActionShowed(false);
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
