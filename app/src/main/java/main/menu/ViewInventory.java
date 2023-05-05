package main.menu;

import main.Game;
import util.Input;

public class ViewInventory implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        System.out.println("\nInventory:");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Item Name\t\tCategory\t\tAmount");
        gm.getCurrentSim().getSimItems().printInv();
        scan.enterUntukLanjut(); 
    }
}
