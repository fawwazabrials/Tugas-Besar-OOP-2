package main.menu;

import main.Game;
import util.Input;

public class Inventory implements Option {

    private Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        // System.out.println("\n    Inventory:");
        // System.out.println(String.format(" %s ", "-----------------------------------------------------"));
        // System.out.println(String.format("| %-15s | %-15s | %-15s |", "Item Name", "Category", "Amount"));
        // System.out.println(String.format("|%s|", "-----------------------------------------------------"));
        gm.getCurrentSim().getSimItems().print();
        scan.enterUntukLanjut(); 
    }
}
