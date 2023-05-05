package main.menu;

import java.util.List;

import item.Furniture;
import main.Game;
import util.Angka;
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
            (new ListObject()).execute(gm);

            int input = -999;
            while (input <= 0) {
                System.out.print("\nENTER OBJEK YANG DITUJU: ");
                input = Angka.stringToInt(scan.nextLine());

                if (input <= 0 || input > furnitures.size()) {
                    System.out.println("Masukkan angka dalam batas objek!");
                } else {
                    gm.getCurrentSim().setCoordinates(furnitures.get(input-1).getY(), furnitures.get(input-1).getX());
                    gm.setOverlapActionShowed(false);
                }

            }
        }
    }
}
