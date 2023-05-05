package main;

import java.util.HashMap;
import java.util.Map;

import exception.NoInputException;
import item.Furniture;
import main.menu.*;
import util.Input;

public class Menu {
    private Game gm;
    private Input scan = Input.getInstance();   

    private Map<String, Option> availableMenuOptions;

    public Menu(Game gm) {
        this.gm = gm;

        availableMenuOptions = new HashMap<String, Option>();
        availableMenuOptions.put("S", new SimInfo());
        availableMenuOptions.put("C", new ChangeJob());
        availableMenuOptions.put("I", new Inventory());  // TODO: Route this
        availableMenuOptions.put("U", new UpgradeHouse());  // TODO: Route this
        availableMenuOptions.put("M", new MoveSim());
        availableMenuOptions.put("E", new EditRoom());
        availableMenuOptions.put("Ad", new AddSim());
        availableMenuOptions.put("Ch", new ChangeSim());
        availableMenuOptions.put("L", new ListObject(false));
        availableMenuOptions.put("G", new GoToObject());
        availableMenuOptions.put("A", new ActionMenu(new Action(gm)));
        availableMenuOptions.put("Sh", new Shop());  // TODO: Route this
        availableMenuOptions.put("X", new Exit());
    }

    public void executeOption(String optionKey) throws IllegalArgumentException {
        Option option = availableMenuOptions.get(optionKey);

        if (option == null) {
            throw new IllegalArgumentException("Tidak ada opsi menu tersebut!");
        }

        option.execute(gm);
    }

    public void showOptions() {
        System.out.println("\n(S)im Info        (C)hange Job  (I)nventory");
        System.out.println("(U)pgrade House   (M)ove Sim          (E)dit Room");
        System.out.println("(Ad)dd Sim        (Ch)ange Sim        (L)ist Object  ");
        System.out.println("(G)o to Object    (A)ction            (Sh)op");
        System.out.println("                 E(X)it");
    }

    public void getInput() {
        try {
            String input = scan.getInput("\nMasukkan opsi yang dipilih: ");

            if (input.split(" ")[0].equals("testingcheats")) {
                if (input.split(" ").length == 2) {
                    gm.getCheat().cheatOptions(input.split(" ")[1]);
                } 
    
                if (input.split(" ").length == 3) { // money/mood/health/hunger cheat
                    gm.getCheat().cheatOptions(input.split(" ")[1], Integer.parseInt(input.split(" ")[2]));
                } 
            } 
            
            else {
                executeOption(input);
            }
        }
        catch (NoInputException e) {
            // ignore
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void askOverlapAction() {
        Furniture overlap = gm.getOverlapFurniture();
        if (overlap != null && !gm.isOverlapActionShowed()) {
            String show = "\nApakah anda ingin ";

            switch (overlap.getAction()) {
                case "Sleep":
                    show += "tidur";
                    break;
                case "Poop":
                    show += "buang air";
                    break;
                case "Eat":
                    show += "makan";
                    break;
                case "Cook":
                    show += "memasak";
                    break;
                case "See Time":
                    show += "melihat waktu";
                    break;
            }

            show += "? (Y/N)";

            try {
                String input = scan.getInput(show);

                if (input.equals("Y")) {
                    gm.getAction().execute(overlap.getAction());
                } else if (input.equals("N")) {
                    // ignore
                }

            } catch (NoInputException e) {
                // ignore
            }

            gm.setOverlapActionShowed(true);
        }

        else if (overlap == null) gm.setOverlapActionShowed(false);
    }
}
