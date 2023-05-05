package main.menu;

import java.util.List;

import item.Furniture;
import main.Game;
import util.Input;

public class ListObject implements Option {

    Input scan = Input.getInstance();
    private boolean showEnter;

    public ListObject(boolean showEnter) {
        this.showEnter = showEnter;
    }

    @Override
    public void execute(Game gm) {
        List<Furniture> furnitures = gm.getCurrentSim().getRoom().getFurnitures();
        
        if (furnitures.size() == 0) {
            System.out.println("\nTidak ada objek di dalam ruangan!");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nBerikut adalah objek yang ada di dalam ruangan");

            System.out.println(String.format(" %s ", "--------------------------"));
            System.out.println(String.format("| %-3s | %-18s |", "No", "Objek"));
            System.out.println(String.format("|%s|", "--------------------------"));
            for (int i=0; i<furnitures.size(); i++) {
                System.out.println(String.format("| %-3s | %-18s |", i+1, furnitures.get(i).getName()));
            }
            System.out.println(String.format(" %s ", "--------------------------"));

            scan.enterUntukLanjut();
        }
    }

}
