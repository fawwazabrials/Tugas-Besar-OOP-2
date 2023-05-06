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
                    x = scan.getIntegerInput("ENTER X COORDINATE: ");
                    y = scan.getIntegerInput("ENTER Y COORDINATE: ");

                    if (x < 0 || x > gm.getCurrentSim().getRoom().getLength() || y < 0 || y > gm.getCurrentSim().getRoom().getWidth()) {
                        System.out.println("\nKoordinat di luar batas!");
                    } else {
                        gm.getCurrentSim().getRoom().addFurniture(furniture, y, x);
                        gm.getCurrentSim().getSimItems().removeItem(furniture);
                        System.out.println("\nFurniture berhasil ditambahkan!");
                        scan.enterUntukLanjut();
                        getInput = false;
                    }
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
                        x = scan.getIntegerInput("ENTER X COORDINATE: ");
                        y = scan.getIntegerInput("ENTER Y COORDINATE: ");

                        if (x < 0 || x > gm.getCurrentSim().getRoom().getLength() || y < 0 || y > gm.getCurrentSim().getRoom().getWidth()) {
                            System.out.println("\nKoordinat di luar batas!");
                        } else {
                            gm.getCurrentSim().getSimItems().addItem(gm.getCurrentSim().getRoom().removeFurniture(y, x));    
                            System.out.println("\nFurniture berhasil dihapus!"); 
                            scan.enterUntukLanjut(); 
                            getInput = false;
                        }
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
                        x = scan.getIntegerInput("ENTER X COORDINATE: ");
                        y = scan.getIntegerInput("ENTER Y COORDINATE: ");

                        System.out.println("\nNew coordinate:");
                        newX = scan.getIntegerInput("ENTER NEW X COORDINATE: ");
                        newY = scan.getIntegerInput("ENTER NEW Y COORDINATE: ");

                        if (x < 0 || x > gm.getCurrentSim().getRoom().getLength() || y < 0 || y > gm.getCurrentSim().getRoom().getWidth() || newX < 0 || newX > gm.getCurrentSim().getRoom().getLength() || newY < 0 || newY > gm.getCurrentSim().getRoom().getWidth()) {
                            System.out.println("\nKoordinat di luar batas!");
                        } else {
                            gm.getCurrentSim().getRoom().moveFurniture(y, x, newY, newX);
                            System.out.println("\nFurniture berhasil dipindahkan!");
                            scan.enterUntukLanjut();
                            getInput = false;
                        }
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
