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
        // Belom ada currentHouse di Sim
        // if (! currentSim.getCurrentHouse().equals(currentSim.getHouse()))
        if (!gm.currentHouse().equals(gm.getCurrentSim().getHouse())) {
            throw new IllegalArgumentException("Sim is not in their house.");
        }

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

            System.out.print("ENTER X COORDINATE: ");
            int x = scan.nextInt();
            System.out.print("ENTER Y COORDINATE: ");
            int y = scan.nextInt();

            gm.getCurrentSim().getRoom().addFurniture(furniture, x, y);
        } else if (input.equals("R")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                System.out.println("\nFurniture want to remove:");
                System.out.print("ENTER X COORDINATE: ");
                int x = scan.nextInt();
                System.out.print("ENTER Y COORDINATE: ");
                int y = scan.nextInt();

                gm.getCurrentSim().getSimItems().addItem(gm.getCurrentSim().getRoom().removeFurniture(x, y));      
            }
        } else if (input.equals("M")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                System.out.println("\nFurniture want to move:");
                System.out.print("ENTER X COORDINATE: ");
                int x = scan.nextInt();
                System.out.print("ENTER Y COORDINATE: ");
                int y = scan.nextInt();

                System.out.println("\nNew coordinate:");
                System.out.print("ENTER NEW X COORDINATE: ");
                int newX = scan.nextInt();
                System.out.print("ENTER NEW Y COORDINATE: ");
                int newY = scan.nextInt();

                gm.getCurrentSim().getRoom().moveFurniture(x, y, newX, newY);
            }
        } else {
            throw new IllegalArgumentException("Invalid input.");
        }
    }
    
}
