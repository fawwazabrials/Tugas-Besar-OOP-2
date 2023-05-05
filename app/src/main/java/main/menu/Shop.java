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
        System.out.println("\n----- JENIS ITEM YANG BISA DIBELI -----");
        System.out.println("1. Ingredients");
        System.out.println("2. Furniture");

        int input = 0;
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

        System.out.println("\nList of Items:");
        System.out.println("--------------------------------------------------------------------");
        if (isFurniture) {
            System.out.println("Furniture Name\t\tPrice\t\tDescription");
            System.out.println("1. Kasur Single\t\t50\t\tSize: 4x1, Aksi: sleep");
            System.out.println("2. Kasur Queen Size\t100\t\tSize: 4x2, Aksi: sleep");
            System.out.println("3. Kasur King Size\t150\t\tSize: 5x2, Aksi: sleep");
            System.out.println("4. Toilet\t\t50\t\tSize: 1x1, Aksi: poop");
            System.out.println("5. Kompor Gas\t\t100\t\tSize: 2x1, Aksi: cook");
            System.out.println("6. Kompor Listrik\t200\t\tSize: 1x1, Aksi: cook");
            System.out.println("7. Meja dan Kursi\t50\t\tSize: 3x3, Aksi: eat");
            System.out.println("8. Jam\t\t\t10\t\tSize: 1x1, Aksi: see time");
        } else {
            System.out.println("Ingredients Name\tPrice\t\tDescription");
            System.out.println("1. Nasi\t\t\t5\t\tHunger point: 5");
            System.out.println("2. Kentang\t\t3\t\tHunger point: 4");
            System.out.println("3. Ayam\t\t\t10\t\tHunger point: 8");
            System.out.println("4. Sapi\t\t\t12\t\tHunger point: 15");
            System.out.println("5. Wortel\t\t3\t\tHunger point: 2");
            System.out.println("6. Bayam\t\t3\t\tHunger point: 2");
            System.out.println("7. Kacang\t\t2\t\tHunger point: 2");
            System.out.println("8. Susu\t\t\t2\t\tHunger point: 1");
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
    }
    
}
