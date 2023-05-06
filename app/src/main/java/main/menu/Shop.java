package main.menu;

import exception.NoInputException;
import item.Furniture;
import item.Ingredients;
import item.Other;
import main.Game;
import util.Input;

public class Shop implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        try {
            String input = scan.getInput("Beli Item atau Jual item? (B/J) ");

            if (input.equals("B")) { // kode beli
                System.out.println(String.format("\n %s ", "-- JENIS ITEM YANG BISA DIBELI --"));
                System.out.println(String.format("| %-31s |", "1. Furniture"));
                System.out.println(String.format("| %-31s |", "2. Ingredients"));
                System.out.println(String.format("| %-31s |", "3. Other"));
                System.out.println(String.format(" %s ", "---------------------------------"));

                String category = scan.getInput("Beli menu yang ingin dilihat (1/2/3) ");

                if (category.equals("1")) {
                    System.out.println(String.format("\n %s ", "--------------------------------------------------------------------"));
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
                    System.out.println(String.format("| %-21s | %-6s | %-33s |", "Komputer", "30", "Size: 1x1, Aksi: play game"));
                    System.out.println(String.format("| %-21s | %-6s | %-33s |", "TV", "20", "Size: 1x1, Aksi: watch TV"));
                    System.out.println(String.format("| %-21s | %-6s | %-33s |", "Rak buku", "15", "Size: 1x1, Aksi: read"));
    
                    System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));

                    input = scan.getInput("Masukkan nama item yang ingin dibeli: ");
                    switch (input) {
                        case "Kasur Single": 
                            gm.getCurrentSim().buyItem(new Furniture("kasur single", -1, -1));
                            break;
                        case "Kasur Queen Size": 
                            gm.getCurrentSim().buyItem(new Furniture("kasur queen size", -1, -1));
                            break;
                        case "Kasur King Size": 
                            gm.getCurrentSim().buyItem(new Furniture("kasur king size", -1, -1));
                            break;
                        case "Toilet": 
                            gm.getCurrentSim().buyItem(new Furniture("toilet", -1, -1));
                            break;
                        case "Kompor Gas": 
                            gm.getCurrentSim().buyItem(new Furniture("kompor gas", -1, -1));
                            break;
                        case "Kompor Listrik": 
                            gm.getCurrentSim().buyItem(new Furniture("kompor listrik", -1, -1));
                            break;
                        case "Meja dan Kursi": 
                            gm.getCurrentSim().buyItem(new Furniture("meja dan kursi", -1, -1));
                            break;
                        case "Jam": 
                            gm.getCurrentSim().buyItem(new Furniture("jam", -1, -1));
                            break;
                        case "Komputer": 
                            gm.getCurrentSim().buyItem(new Furniture("komputer", -1, -1));
                            break;
                        case "TV": 
                            gm.getCurrentSim().buyItem(new Furniture("tv", -1, -1));
                            break;
                        case "Rak buku": 
                            gm.getCurrentSim().buyItem(new Furniture("rak buku", -1, -1));
                            break;
                        default:
                            System.out.println("Tidak ada barang dengan nama tersebut!");
                    }
                }

                else if (category.equals("2")) {
                    System.out.println(String.format("\n %s ", "------------------------------------------------------"));
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

                    input = scan.getInput("Masukkan nama item yang ingin dibeli: ");
                    switch (input) {
                        case "Nasi": 
                            gm.getCurrentSim().buyItem(new Ingredients("nasi"));
                            break;
                        case "Kentang": 
                            gm.getCurrentSim().buyItem(new Ingredients("kentang"));
                            break;
                        case "Ayam": 
                            gm.getCurrentSim().buyItem(new Ingredients("ayam"));
                            break;
                        case "Sapi":
                            gm.getCurrentSim().buyItem(new Ingredients("sapi"));
                            break;
                        case "Wortel": 
                            gm.getCurrentSim().buyItem(new Ingredients("wortel"));
                            break;
                        case "Bayam": 
                            gm.getCurrentSim().buyItem(new Ingredients("bayam"));
                            break;
                        case "Kacang": 
                            gm.getCurrentSim().buyItem(new Ingredients("kacang"));
                            break;
                        case "Susu": 
                            gm.getCurrentSim().buyItem(new Ingredients("susu"));
                            break;
                        default:
                            System.out.println("Tidak ada barang dengan nama tersebut!");
                    }
                }

                else if (category.equals("3")) {
                    System.out.println(String.format(" %s ", "------------------------------------------------------"));
                    System.out.println(String.format("| %-21s | %-6s | %-19s |", "Item name", "Price", "Description"));
                    System.out.println(String.format("|%s|", "------------------------------------------------------"));
                    System.out.println(String.format("| %-21s | %-6s | %-19s |", "HP", "5", "Aksi: gamble"));
                    System.out.println(String.format("| %-21s | %-6s | %-19s |", "Sheet QnA", "3", "Aksi: read QnA"));
                    System.out.println(String.format(" %s ", "------------------------------------------------------"));

                    input = scan.getInput("Masukkan nama item yang ingin dibeli: ");
                    switch (input) {
                        case "HP": 
                            gm.getCurrentSim().buyItem(new Other("HP"));
                            break;
                        case "Sheet QnA": 
                            gm.getCurrentSim().buyItem(new Other("Sheet QnA"));
                            break;
                        default:
                            System.out.println("Tidak ada barang dengan nama tersebut!");
                    }
                    
                }

                else {
                    System.out.println("Masukkan angka dalam batasan pilihan!");
                }
                
                scan.enterUntukLanjut();
            }

            else if (input.equals("J")) { // kode jual
                gm.getCurrentSim().getSimItems().print();

                input = scan.getInput("Masukkan nama barang yang ingin dijual: ");
                gm.getCurrentSim().sellItem(input);
                System.out.println("Barang berhasil dijual!");
                scan.enterUntukLanjut();
            }
        }
        catch (NoInputException e) {
            // ignore
        }
        catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            scan.enterUntukLanjut();
        }

    }
}
