package main.menu;

import java.util.Map;

import item.Furniture;
import item.Item;
import main.Game;
import util.Input;

public class EditRoom implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        if (!gm.getCurrentSim().getCurrHouse().equals(gm.getCurrentSim().getHouse())) {
            throw new IllegalArgumentException("Sim is not in their house.");
        }

        gm.renderCurrentView();

        System.out.println("\n(A)dd Furniture  (R)emove Furniture  (M)ove Furniture");
        System.out.print("ENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("A")) {
            System.out.println("\nList of Furnitures:");
            System.out.println("==========================");
            
            if (gm.getCurrentSim().getSimItems().getItems("furniture").isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                for (Map.Entry<Item, Integer> item : gm.getCurrentSim().getSimItems().getItems("furniture").entrySet()) {
                        System.out.println(item.getKey().getName());
                        System.out.println("--------------------------");
                }
            }

            System.out.print("\nENTER FURNITURE NAME: ");
            String itemName = scan.nextLine();
            Furniture furniture = null;
             
            for (Map.Entry<Item, Integer> item : gm.getCurrentSim().getSimItems().getItems("furniture").entrySet()) {
                if (itemName.equals(item.getKey().getName())) {
                    furniture = (Furniture)item.getKey();
                }
            }

            if (furniture == null) {
                throw new IllegalArgumentException("Furniture not found.");
            }

            int x = -999;
            int y = -999;

            try {
                x = scan.getIntegerInput("ENTER X COORDINATE: ");
                y = scan.getIntegerInput("ENTER Y COORDINATE: ");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            }

            gm.getCurrentSim().getRoom().addFurniture(furniture, x, y);
            gm.getCurrentSim().getSimItems().removeItem(furniture);
            System.out.println("\nFurniture berhasil ditambahkan!");
            scan.enterUntukLanjut();
        } else if (input.equals("R")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                System.out.println("\nFurniture want to remove:");
                int x = -999;
                int y = -999;

                try {
                    x = scan.getIntegerInput("ENTER X COORDINATE: ");
                    y = scan.getIntegerInput("ENTER Y COORDINATE: ");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    scan.enterUntukLanjut();
                }

                gm.getCurrentSim().getSimItems().addItem(gm.getCurrentSim().getRoom().removeFurniture(x, y));    
                System.out.println("\nFurniture berhasil dihapus!"); 
                scan.enterUntukLanjut(); 
            }
        } else if (input.equals("M")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                System.out.println("\nFurniture want to move:");
                int x = -999;
                int y = -999;

                try {
                    x = scan.getIntegerInput("ENTER X COORDINATE: ");
                    y = scan.getIntegerInput("ENTER Y COORDINATE: ");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    scan.enterUntukLanjut();
                }

                System.out.println("\nNew coordinate:");
                int newX = -999;
                int newY = -999;

                try {
                    newX = scan.getIntegerInput("ENTER NEW X COORDINATE: ");
                    newY = scan.getIntegerInput("ENTER NEW Y COORDINATE: ");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    scan.enterUntukLanjut();
                }

                gm.getCurrentSim().getRoom().moveFurniture(x, y, newX, newY);
                System.out.println("\nFurniture berhasil dipindahkan!");
                scan.enterUntukLanjut();
            }
        } else {
            throw new IllegalArgumentException("Invalid input.");
        }
    }
}
