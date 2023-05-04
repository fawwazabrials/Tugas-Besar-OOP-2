package main;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import entity.Job;
import entity.Sim;
import exception.NoInputException;
import util.Angka;
import util.ClearScreen;
import util.Input;
import item.*;
import map.*;
import java.util.*;

public class Game {
    // IMPORTANT GAME ATTRIBUTES
    private static Renderable currentView;
    private static String currentHouse;
    private static Sim currentSim;
    private static World world;
    private static Clock clock;

    public static Clock getClock() {return clock;}
    public static World getWorld() {return world;}
    public static void setWorld(World world) {Game.world = world;}
    public static void setCurrentView(Renderable currentView) {Game.currentView = currentView;}
    public static Renderable getCurrentView() {return currentView;}
    public static void setCurrentSim(Sim currentSim) {Game.currentSim = currentSim;}
    public static Sim getCurrentSim() {return currentSim;}
    public static void setCurrentHouse(String currentHouse) {Game.currentHouse = currentHouse;}
    public static String getCurrentHouse() {return currentHouse;}

    private GameSimOption simOption = new GameSimOption(this);
    private static long gameTime, day;

    // CHEAT OPTIONS
    private static boolean skiptime, fastbuild, fastshop, addinfinitesim = false;

    private long dayLastSimAdded = -1;
    protected Input scan = Input.getInstance();

    public Game() {
        overlapActionShowed = false;
        gameTime = 0;
        day = 0;
        world = World.getInstance();
        startNew();
        currentHouse = currentSim.getName();
    }

    public void showGamePanel() {
        // TODO: ini bawah nanti di uncomment
        // ClearScreen.clear();

        if (currentSim.isDead()) {
            currentSim.killSim();
            world.getSims().remove(currentSim);
            showDeadScreen();
        }

        else {
            System.out.println("last sleep: " + (Game.getTime() - currentSim.getTimeLastSleep()));
            System.out.println("time: " + Game.getTime());
            System.out.println("::: " + ((Game.getTime() - currentSim.getTimeLastSleep()) >= 60*10));
            System.out.println(currentSim.getTrackUpdates().isAlive());
            showRender();
            showOverlapAction();
            showOptions();
            getInput();
        }
    }

    public void showDeadScreen() {
        System.out.println("\nSim kamu mati!");

        if (world.getSims().size() == 0) {
            showGameOverScreen();
        } else {
            changeSimOption(true);
        }
    }

    public void showGameOverScreen() {
        System.out.println("Semua Sim kamu mati! Kamu kalah!");
        System.exit(0);
    }

    public void showRender() {
        char[][] rendered = currentView.render();

        rendered[currentSim.getY()][currentSim.getX()] = 'S'; // tampilin sim
        System.out.println("Rumah " + currentHouse + " - " + currentSim.getRoom().getRoomName());

        for (int i=0; i<6; i++) {
            for (int j=0; j<6; j++) {
                System.out.print(rendered[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void showOptions() {
        System.out.println("\n(S)im Info        (C)hange Job  (I)nventory");
        System.out.println("(U)pgrade House   (M)ove Sim          (E)dit Room");
        System.out.println("(Ad)dd Sim        (Ch)ange Sim        (L)ist Object  ");
        System.out.println("(G)o to Object    (A)ction            (S)hop");
        System.out.println("                 E(X)it");
    }

    public void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("Ch")) {
            changeSimOption(false);
        }
        
        else if (input.equals("S")) {
            showSimInfo();
        }

        else if (input.equals("C")) {
            changeJobOption();
        }
        
        else if (input.equals("Ad")) {
            addSimOption();
        }

        else if (input.equals("A")) {
            actionOptions();
        }

        else if (input.equals("X")) {
            System.exit(0);
        } 
        else if (input.equals("M")){
            try {
                moveRoomOption();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            }
        }

        // TODO: Uncomment kalo udah dibenerin
        // else if (input.equals("E")) {
        //     try {
        //         editRoomOption();
        //     } catch (Exception e) {
        //         System.out.println(e.getMessage());
        //         scan.enterUntukLanjut();
        //     }
        // } 
        
        else if (input.equals("U")) {
            try {
                simOption.upgradeHouse();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            }
        }

        else if (input.equals("L")) {
            listObjectOption(true);
        }

        else if (input.equals("G")) {
            goToObjectOption();
        }

        else if (input.split(" ")[0].equals("testingcheats")) {
            try {
                if (input.split(" ").length == 2) {
                    cheatOptions(input.split(" ")[1], 0);
                } 
    
                if (input.split(" ").length == 3) { // money/mood/health/hunger cheat
                    cheatOptions(input.split(" ")[1], Integer.parseInt(input.split(" ")[2]));
                } 
            } catch (Exception e) {
                // ignore
            }
        }

        else {
            System.out.println("\nMasukkan input sesuai dengan opsi diatas!");
            scan.enterUntukLanjut();
        }
    }

    public void changeJobOption() {
        System.out.println("\n    Pekerjaan yang ada: ");
        System.out.println(String.format(" %s ", "--------------------------"));
        System.out.println(String.format("| %-15s | %-6s |", "Pekerjaan", "Gaji"));
        System.out.println(String.format("|%s|", "--------------------------"));
        for (String s : Job.getAvailableJobsList()) {
            System.out.println(String.format("| %-15s | %-6s |", s, Job.getAvailableJobs().get(s)));
        }
        System.out.println(String.format(" %s ", "--------------------------"));

        System.out.println("\nSim hanya bisa mengganti pekerjaan jika sudah bekerja selama minimal 1 hari di pekerjaan lamanya");
        System.out.println("Sim juga harus membayarkan setengah gaji dari pekerjaan baru untuk mengganti pekerjaan");

        try {
            String input = scan.getInput("\nMasukkan pekerjaan yang dipilih: ");

            try {
                currentSim.changeJob(new Job(input));
                System.out.println("\nPekerjaan sim berhasil diganti!");
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            scan.enterUntukLanjut();

        } catch (NoInputException e) {
            // ingoner
        }   
        
    }

    public void cheatOptions(String cheat, int val) {
        switch (cheat) {
            case "money":
                currentSim.setMoney(val);
                break;

            case "mood":
                currentSim.setMood(val);
                break;

            case "health":
                currentSim.setHealth(val);
                break;

            case "hunger":
                currentSim.setHunger(val);
                break;

            case "skiptime":
                skiptime = !skiptime;
                break;

            case "fastbuild":
                fastbuild = !fastbuild; // TODO: implement this cheat
                break;

            case "fastshop":
                fastshop = !fastshop; // TODO: implement this cheat
                break;

            case "killcurrentsim":
                currentSim.killSim();
                break;

            case "addinfinitesim":
                addinfinitesim = !addinfinitesim;
                break;

            case "forwardtime":
                addTime(val);
                break;
        }
    }

    public void showSimInfo() {
        System.out.println("\n------ SIM INFO ------");
        System.out.println("Nama: " + currentSim.getName());
        System.out.println("Pekerjaan: " + currentSim.getJobName());
        System.out.println("Kesehatan: " + currentSim.getHealth());
        System.out.println("Kekenyangan: " + currentSim.getHunger());
        System.out.println("Mood: " + currentSim.getMood());
        System.out.println("Uang: " + currentSim.getMoney());

        scan.enterUntukLanjut();
    }

    public void showOverlapAction() {
        Furniture overlap = getOverlapFurniture();

        if (overlap != null && !overlapActionShowed) {
            overlapAction(overlap, true);
            overlapActionShowed = true;
        } else if (overlap == null) overlapActionShowed = false;
    }

    public void overlapAction(Furniture overlap, boolean ask) {
        if (overlap.getAction().equals("sleep")) {
            if (ask) {
                System.out.print("\nApakah anda ingin tidur? (Y/N) ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    simOption.sleep();
                }
            } else {
                simOption.sleep();

            }
        }
        else if (overlap.getAction().equals("poop")) {
            if (ask) {
                System.out.print("\nApakah anda ingin buang air? (Y/N) ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    simOption.poop();
                }
            } else {
                simOption.poop();

            }
        }
        else if (overlap.getAction().equals("cook")) {
            if (ask) {
                System.out.print("\nApakah anda ingin memasak? (Y/N) ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    // TODO : tambahin method buat cook
                    showDishTable();
                }
            } else {
                // TODO : tambahin method buat cook

            }
        }
        else if (overlap.getAction().equals("eat")) {
            if (ask) {
                System.out.print("\nApakah anda ingin makan? (Y/N) ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    // TODO : tambahin method buat eat
                }
            } else {
                    // TODO : tambahin method buat eat
            }
        }
        else if (overlap.getAction().equals("seetime")) {
            if (ask) {
                // apa ini langsung tunjukin waktu aja ya? hmm
                System.out.print("\nApakah anda ingin melihat waktu? (Y/N) ");
                String input = scan.nextLine();
                if (input.equals("Y")) {
                    simOption.seeTime();
                }
            } else {
                simOption.seeTime();
            }
        }
    }

    public Furniture getOverlapFurniture() {
        return currentSim.getRoom().getRoomGrid()[currentSim.getY()][currentSim.getX()];
    }

    public void goToObjectOption() {
        List<Furniture> furnitures = currentSim.getRoom().getFurnitures();
        
        if (furnitures.size() == 0) {
            System.out.println("\nTidak ada objek di dalam ruangan!");
            scan.enterUntukLanjut();
        } else {
            listObjectOption(false);

            int input = -999;
            while (input <= 0) {
                System.out.print("\nENTER OBJEK YANG DITUJU: ");
                input = Angka.stringToInt(scan.nextLine());

                if (input <= 0 || input > furnitures.size()) {
                    System.out.println("Masukkan angka dalam batas objek!");
                } else {
                    currentSim.goToObject(furnitures.get(input-1).getY(), furnitures.get(input-1).getX());
                    overlapActionShowed = false;
                }

            }
        }
    }

    public void listObjectOption(boolean enter) {
        List<Furniture> furnitures = currentSim.getRoom().getFurnitures();
        
        if (furnitures.size() == 0) {
            System.out.println("\nTidak ada objek di dalam ruangan!");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nBerikut adalah objek yang ada di dalam ruangan");
            for (int i=0; i<furnitures.size(); i++) {
                System.out.println((i+1) + ". " + furnitures.get(i).getName());
            }
            if (enter) scan.enterUntukLanjut();
        }

    }

    public void actionOptions() {
        int actionNum = 4;

        System.out.println("\n----- AKSI YANG BISA DILAKUKAN -----");
        System.out.println("1. Work");
        System.out.println("2. Workout");
        System.out.println("3. Visit");
        System.out.println("4. Stargaze");

        Furniture furniture = getOverlapFurniture();
        if (furniture != null) {
            actionNum++;
            System.out.println(actionNum + ". " + furniture.getAction());
        }

        int input = -999;
        while (input <= 0) {
            System.out.print("\nMASUKKAN AKSI YANG INGIN DILAKUKAN: ");
            input = Angka.stringToInt(scan.nextLine());

            if (input <= 0 || input > actionNum) {
                System.out.println("Masukkan angka dalam batas aksi!");
            } else {
                switch (input) {
                    case 1:
                        simOption.work();
                        break;

                    case 2:
                        simOption.workout();
                        break;
                    
                    case 3:
                        simOption.visit();
                        break;
                    
                    case 4:
                        // TODO: Tambahin simAction stargaze disini
                        break;

                    case 5:
                        overlapAction(furniture, false);
                        break;
                }
            }
        }

    }

    public void changeSimOption(boolean deadscreen) {
        int simNum = world.getSims().size();

        if (simNum <= 1 && !deadscreen) {
            System.out.println("\nKamu hanya memiliki 1 sim! Silahkan buat sim lain untuk dimainkan.");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nPilih sim mana yang ingin dimainkan: ");
            int cnt = 1;
            for (int i=0; i<simNum; i++) {
                if (world.getSims().get(i) == currentSim) { // Bukan sim yang sekarang lagi dimainin
                    System.out.println(cnt + ". " + world.getSims().get(i).getName() + " (Sim sekarang)");
                } else {
                    System.out.println(cnt + ". " + world.getSims().get(i).getName());
                }
                cnt++;
            }

            int input = -999;
            while (input <= 0) {
                System.out.print("PILIH SIM: ");
                input = Angka.stringToInt(scan.nextLine());

                if (input <= 0 || input > simNum) {
                    System.out.println("\nMasukan angka di dalam batas sim!");
                    input = -1;
                } else {
                    if (world.getSims().get(input-1) == currentSim) {
                        System.out.println("\nTidak bisa mengganti ke sim yang sedang dimainkan!");
                        scan.enterUntukLanjut();
                    } else {
                        changeSim(world.getSims().get(input-1));
                    }
                }
            }
        }
    }

    public void addSimOption() {
        if (day <= dayLastSimAdded) {
            System.out.println("\nSim tidak bisa ditambahkan! Sim hanya bisa ditambahkan setiap 1 hari.");
            System.out.println(day + " <= " + dayLastSimAdded);
            scan.enterUntukLanjut();
        } else {
            System.out.print("ENTER SIM NAME: ");
            String simName = scan.nextLine();

            boolean found = false;
            while (!found) {
                try {
                    world.addHouse(ThreadLocalRandom.current().nextInt(0, 64 + 1), ThreadLocalRandom.current().nextInt(0, 64 + 1), simName);
                    found = true;
                } catch (Exception e) {
                    
                }
            }

            // DEBUG: ganti day jadi -1; 
            // TODO: NANTI INI GANTI dayLastSimAdded = day;
            dayLastSimAdded = -1;
        }
    }

    public void startNew() {
        addSimOption();
        currentSim = world.getSims().get(0);
        currentView = currentSim.getRoom();
    }

    public void changeSim(Sim newSim) {
        currentSim = newSim;
        changeView(currentSim.getRoom());
        currentHouse = currentSim.getName();
    }

    public void changeView(Renderable newView) {
        currentView = newView;
    }

    public void addTime(int secs) {
        gameTime += secs;
        day = gameTime / 720;
    }

    

    public static long getTime() {return gameTime;}
    public static long getDay() {return day;}


    public House currentHouse() {
        House house = null;
        for (Sim sim : world.getSims()) {
            if (sim.getHouse().getRooms().contains(currentSim.getRoom())) {
                house = sim.getHouse();
            }
        }
        return house;
    }

    public void moveRoomOption(){
        // Belom ada currentHouse di Sim
        // if (currentSim.getCurrentHouse() == null)
        if (currentHouse() == null) {
            throw new IllegalArgumentException("Sim is not in any house.");
        }

        System.out.println("\nHouse Map :");
        System.out.println("==========================");
        currentHouse().printHouse();
        

        System.out.print("ENTER ROOM NAME: ");
        String roomName = scan.nextLine();
        Room room = null;
        for (Room r : currentHouse().getRooms()) {
            if (r.getRoomName().equals(roomName)) {
                room = r;
            }
        }

        if (room == null) {
            throw new IllegalArgumentException("Room not found.");
        }

        currentSim.move(room);
        changeView(currentSim.getRoom());
    }

    public void editRoomOption(){
        // Belom ada currentHouse di Sim
        // if (! currentSim.getCurrentHouse().equals(currentSim.getHouse()))
        if (! currentHouse().equals(currentSim.getHouse())) {
            throw new IllegalArgumentException("Sim is not in their house.");
        }

        System.out.println("\n(A)dd Furniture  (R)emove Furniture  (M)ove Furniture");
        System.out.print("ENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("A")) {
            System.out.println("\nList of Furnitures:");
            System.out.println("==========================");
            
            if (currentSim.getSimItems().getItems("furniture").isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                for (Map.Entry<Item, Integer> item : currentSim.getSimItems().getItems("furniture").entrySet()) {
                        System.out.println(item.getKey().getName());
                        System.out.println("--------------------------");
                }
            }

            System.out.print("\nENTER FURNITURE NAME: ");
            String itemName = scan.nextLine();
            Furniture furniture = null;
             
            for (Map.Entry<Item, Integer> item : currentSim.getSimItems().getItems("furniture").entrySet()) {
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

            currentSim.getRoom().addFurniture(furniture, x, y);
        } else if (input.equals("R")) {
            if (currentSim.getRoom().getFurnitures().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                System.out.println("\nFurniture want to remove:");
                System.out.print("ENTER X COORDINATE: ");
                int x = scan.nextInt();
                System.out.print("ENTER Y COORDINATE: ");
                int y = scan.nextInt();

                currentSim.getSimItems().addItem(currentSim.getRoom().removeFurniture(x, y));      
            }
        } else if (input.equals("M")) {
            if (currentSim.getRoom().getFurnitures().isEmpty()) {
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

                currentSim.getRoom().moveFurniture(x, y, newX, newY);
            }
        } else {
            throw new IllegalArgumentException("Invalid input.");
        }
    }

    public void showDishTable() {
        System.out.println("\nList of Dish:");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Dish Name\t\tHunger Point\tRecipe");
        System.out.println("Nasi ayam\t\t16\t\tNasi, Ayam");
        System.out.println("Nasi kari\t\t30\t\tNasi, Kentang, Wortel, Sapi");
        System.out.println("Susu kacang\t\t5\t\tSusu, Kacang");
        System.out.println("Tumis sayur\t\t5\t\tWortel, Bayam");
        System.out.println("Bistik\t\t\t22\t\tKentang, Sapi");
    }

    private class Clock {
        private int gameTime, day;

        public int getGameTime() {return gameTime;}
        public int getDay() {return day;}
        
        public void forwardTime(int time) {
            gameTime = gameTime+time;
        }
        
        public void moveTime(int time) {
            int secs = time / 1000;
    
            for (int i=0; i<secs; i++) {
                if (!currentSim.isDead()) {
                    try {
                        if (!skiptime) {
                            Thread.sleep(1000); // 1 second
                        } forwardTime(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }    
                } 
            }
        }
    }
}
