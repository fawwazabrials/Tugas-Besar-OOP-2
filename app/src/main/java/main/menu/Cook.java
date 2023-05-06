package main.menu;

import exception.NoInputException;
import exception.SimIsDeadException;
import main.Game;
import util.Input;

public class Cook implements Option{

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        showDishTable();

        try {
            String input = scan.getInput("Masukkan nama dish yang ingin dimasak: ");
            gm.getCurrentSim().cook(input);

        } catch (NoInputException e) {
            // ignore
        } catch (SimIsDeadException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            scan.enterUntukLanjut();
        }
    }

    public void showDishTable() {
        System.out.println("\n    List of Dish:");
        System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Dish Name", "Hunger Point", "Recipe"));
        System.out.println(String.format("|%s|", "--------------------------------------------------------------------"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Nasi Ayam", "16", "Nasi, Ayam"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Nasi Kari", "30", "Nasi, Kentang, Wortel, Sapi"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Susu Kacang", "5", "Susu, Kacang"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Tumis Sayur", "5", "Wortel, Bayam"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Bistik", "22", "Kentang, Sapi"));
        System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
    }
    
}
