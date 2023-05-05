package main.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.NoInputException;
import item.Furniture;
import main.Game;
import util.Angka;
import util.Input;

public class ActionMenu implements Option {

    Input scan = Input.getInstance();
    Action action;

    public ActionMenu(Action action) {
        this.action = action;
    }


    @Override
    public void execute(Game gm) {

        // fill available actions
        List<String> availableAction = action.getListOfAvailableAction();


        // print available actions
        System.out.println("\n----- AKSI YANG BISA DILAKUKAN -----");

        System.out.println(String.format(" %s ", "--------------------------"));
        System.out.println(String.format("| %-3s | %-18s |", "No", "Aksi"));
        System.out.println(String.format("|%s|", "--------------------------"));
        int cnt = 1;
        for (String s : availableAction) {
            System.out.println(String.format("| %-3s | %-18s |", cnt++, s));
        }
        System.out.println(String.format(" %s ", "--------------------------"));
        

        // get input
        String input = "";
        boolean getInput = true;
        while (getInput) {
            try {
                input = scan.getInput("\nMasukkan nama aksi yang ingin dilakukan: ");
                
                action.execute(input);

            } catch (NoInputException e) {
                getInput = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
