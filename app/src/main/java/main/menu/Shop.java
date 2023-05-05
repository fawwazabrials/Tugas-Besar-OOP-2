package main.menu;

import item.Furniture;
import item.Ingredients;
import main.Game;
import util.Angka;
import util.Input;

public class Shop implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        int input = -1;
        while (input == -1) {
            System.out.print("\nBELI ATAU JUAL? (B/J) ");
            if(scan.nextLine().equals("B")){
                input = 1;
            }
            else if(scan.nextLine().equals("J")){
                input = 0;
            }
            else {
                System.out.println("Masukkan input yang sesuai!");
                scan.enterUntukLanjut();
            }
        }
        if(input == 1) {
            System.out.println(String.format("\n %s ", "-- JENIS ITEM YANG BISA DIBELI --"));
            System.out.println(String.format("| %-31s |", "1. Ingredients"));
            System.out.println(String.format("| %-31s |", "2. Furniture"));
            System.out.println(String.format(" %s ", "---------------------------------"));

            input = 0;
            boolean isFurniture = false;
            while (input <= 0 || input > 2) {
                System.out.print("\nMASUKKAN ITEM YANG INGIN DIBELI: ");
                input = Angka.stringToInt(scan.nextLine());
                if (input <= 0 || input > 2) {
                    System.out.println("Masukkan angka dalam batas aksi!");
                    scan.enterUntukLanjut();
                } else {
                    if (input == 2) {
                        isFurniture = true;
                    }
                }
            }

            System.out.println("\n    List of Items:");
            if (isFurniture) {
                System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Furniture Name", "Price", "Description"));
                System.out.println(String.format("|%s|", "--------------------------------------------------------------------"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Kasur Single", "50", "Size: 4x1, Aksi: sleep"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Kasur Queen Size", "100", "Size: 4x2, Aksi: sleep"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Kasur King Size", "150", "Size: 5x2, Aksi: sleep"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Toilet", "50", "Size: 1x1, Aksi: poop"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Kompor Gas", "100", "Size: 2x1, Aksi: cook"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Kompor Listrik", "200", "Size: 1x1, Aksi: cook"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Meja dan Kursi", "50", "Size: 3x3, Aksi: eat"));
                System.out.println(String.format("| %-21s | %-6s | %-33s |", "Jam", "10", "Size: 1x1, Aksi: see time"));
                System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
            } else {
                System.out.println(String.format(" %s ", "------------------------------------------------------"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Ingredients Name", "Price", "Description"));
                System.out.println(String.format("|%s|", "------------------------------------------------------"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Nasi", "5", "Hunger points: 5"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Kentang", "3", "Hunger pointss: 4"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Ayam", "10", "Hunger points: 8"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Sapi", "12", "Hunger points: 15"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Wortel", "3", "Hunger points: 2"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Bayam", "3", "Hunger points: 2"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Kacang", "2", "Hunger points: 2"));
                System.out.println(String.format("| %-21s | %-6s | %-19s |", "Susu", "2", "Hunger points: 1"));
                System.out.println(String.format(" %s ", "------------------------------------------------------"));
            }

            input = 0;
            while (input <= 0 || input > 8) {
                System.out.print("\nENTER ITEM NUMBER: ");
                input = Angka.stringToInt(scan.nextLine());
                if (input <= 0 || input > 8) {
                    System.out.println("Masukkan angka dalam batas indeks!");
                    scan.enterUntukLanjut();
                } else {
                    try {
                        switch(input) {
                            case 1: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("kasur single", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("nasi"));
                                }
                                break;
                            case 2: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("kasur queen size", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("kentang"));
                                }
                                break;
                            case 3: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("kasur king size", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("ayam"));
                                }
                                break;
                            case 4: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("toilet", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("sapi"));
                                }
                                break;
                            case 5: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("kompor gas", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("wortel"));
                                }
                                break;
                            case 6: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("kompor listrik", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("bayam"));
                                }
                                break;
                            case 7: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("meja dan kursi", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("kacang"));
                                }
                                break;
                            case 8: 
                                if (isFurniture) {
                                    gm.getCurrentSim().buyItem(new Furniture("jam", -1, -1));
                                } else {
                                    gm.getCurrentSim().buyItem(new Ingredients("susu"));
                                }
                                break;
                        }
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                        scan.enterUntukLanjut();
                    }
                }
            }
        } else {

        }
    }
}
