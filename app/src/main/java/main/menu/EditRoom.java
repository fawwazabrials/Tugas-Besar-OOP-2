package main.menu;

import java.util.InputMismatchException;
import java.util.Map;

import exception.NoInputException;
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
            if (gm.getCurrentSim().getSimItems().getItems("furniture").isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            }

            gm.getCurrentSim().getSimItems().print("furniture");

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
            boolean getInput = true;

            while (getInput) {
                try {
                    y = scan.getIntegerInput("ENTER Y COORDINATE: ");
                    x = scan.getIntegerInput("ENTER X COORDINATE: ");
                    gm.getCurrentSim().getRoom().addFurniture(furniture, x, y);
                    gm.getCurrentSim().getSimItems().removeItem(furniture);
                    System.out.println("\nFurniture berhasil ditambahkan!");
                    scan.enterUntukLanjut();
                    getInput = false;
                } catch (NoInputException e) {
                    getInput = false;
                } catch (InputMismatchException e) {
                    System.out.println("Masukkan angka!");
                }
            }
        } else if (input.equals("R")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                int x = -999;
                int y = -999;
                boolean getInput = true;

                while (getInput) {
                    try {
                        System.out.println("\nFurniture want to remove:");
                        y = scan.getIntegerInput("ENTER Y COORDINATE: ");
                        x = scan.getIntegerInput("ENTER X COORDINATE: ");
                        gm.getCurrentSim().getSimItems().addItem(gm.getCurrentSim().getRoom().removeFurniture(x, y));    
                        System.out.println("\nFurniture berhasil dihapus!"); 
                        scan.enterUntukLanjut(); 
                        getInput = false;
                    } catch (NoInputException e) {
                        getInput = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Masukkan angka!");
                    }
                }
            }
        } else if (input.equals("M")) {
            if (gm.getCurrentSim().getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                int x = -999;
                int y = -999;
                int newX = -999;
                int newY = -999;

                boolean getInput = true;

                while (getInput) {
                    try {
                        System.out.println("\nFurniture want to move:");
                        y = scan.getIntegerInput("ENTER Y COORDINATE: ");
                        x = scan.getIntegerInput("ENTER X COORDINATE: ");

                        System.out.println("\nNew coordinate:");
                        newY = scan.getIntegerInput("ENTER NEW Y COORDINATE: ");
                        newX = scan.getIntegerInput("ENTER NEW X COORDINATE: ");
                        gm.getCurrentSim().getRoom().moveFurniture(y, x, newY, newX);
                        System.out.println("\nFurniture berhasil dipindahkan!");
                        scan.enterUntukLanjut();
                        getInput = false;
                    } catch (NoInputException e) {
                        getInput = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Masukkan angka!");
                    }
                }
            }
        } else {
            System.out.println("\nMasukkan Salah!");
            scan.enterUntukLanjut();
        }
    }
}
