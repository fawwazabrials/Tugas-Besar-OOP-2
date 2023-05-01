package main;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import entity.Sim;
import util.Angka;
import util.ClearScreen;
import util.Input;
import item.*;
import map.*;

public class Game {
    Renderable currentView;
    Sim currentSim;
    World world;
    private GameSimOption simOption = new GameSimOption(this);
    private static long gameTime, day;

    private boolean isUpgrading, overlapActionShowed;
    private final Object upgradeLock = new Object();

    private long dayLastSimAdded = -1;
    protected Input scan = Input.getInstance();

    public Game() {
        overlapActionShowed = false;
        gameTime = 0;
        day = 0;
        world = World.getInstance();
        startNew();
    }

    public void showGamePanel() {
        // TODO: ini bawah nanti di uncomment
        ClearScreen.clear();
        showRender();
        showOverlapAction();
        showOptions();
        getInput();
    }

    public void showRender() {
        char[][] rendered = currentView.render();

        rendered[currentSim.getY()][currentSim.getX()] = 'S'; // tampilin sim

        for (int i=0; i<6; i++) {
            for (int j=0; j<6; j++) {
                System.out.print(rendered[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void showOptions() {
        System.out.println("\n(S)im Info        (C)urrent Location  (I)nventory");
        System.out.println("(U)pgrade House   (M)ove Sim          (E)dit Room");
        System.out.println("(Ad)dd Sim        (Ch)ange Sim        (L)ist Object  ");
        System.out.println("(G)o to Object    (A)ction            (S)hop");
        System.out.println("                 E(X)it");
    }

    public void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("Ch")) {
            changeSimOption();
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
        else if (input.equals("E")) {
            try {
                editRoomOption();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            }
        } 
        else if (input.equals("U")) {
            try {
                upgradeHouseOption();
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

        else {
            System.out.println("\nMasukkan input sesuai dengan opsi diatas!");
            scan.enterUntukLanjut();
        }
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
                    // TODO : tambahin method buat sleep
                }
            } else {
                    // TODO : tambahin method buat sleep

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
                        // TODO: Tambahin simAction work disini
                        break;

                    case 2:
                        simOption.workout();
                        break;
                    
                    case 3:
                        // TODO: Tambahin simAction visit disini
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

    public void changeSimOption() {
        int simNum = world.getSims().size();

        if (simNum <= 1) {
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

    public void showSimInfo(Sim s) {
        
    }

    public void startNew() {
        addSimOption();
        currentSim = world.getSims().get(0);
        currentView = currentSim.getRoom();
    }

    public void changeSim(Sim newSim) {
        currentSim = newSim;
        changeView(currentSim.getRoom());
    }

    public void changeView(Renderable newView) {
        currentView = newView;
    }

    public static void moveTime(int time) {
        int secs = time / 1000;
        
        for (int i=0; i<secs; i++) {
            try {
                Thread.sleep(1000); // 1 second
                gameTime++;
                day = gameTime / 720;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }    
        }
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
        if (! currentHouse().equals(currentSim.getHouse())) {
            throw new IllegalArgumentException("Sim is not in their house.");
        }

        System.out.println("\n(A)dd Furniture  (R)emove Furniture  (M)ove Furniture");
        System.out.print("ENTER COMMAND: ");
        String input = scan.nextLine();

        if (input.equals("A")) {
            System.out.println("\nList of Furnitures:");
            System.out.println("==========================");
            
            if (currentSim.getSimItems().isEmpty()) {
                throw new IllegalArgumentException("No furniture found.");
            } else {
                for (Item item : currentSim.getSimItems()) {
                    if (item instanceof Furniture) {
                        System.out.println(item.getName());
                        System.out.println("--------------------------");
                    }
                }
            }

            System.out.print("\nENTER FURNITURE NAME: ");
            String itemName = scan.nextLine();
            Furniture furniture = null;
             
            for (Item item : currentSim.getSimItems()) {
                if (item instanceof Furniture && itemName.equals(item.getName())) {
                    furniture = (Furniture)item;
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

                currentSim.getSimItems().add(currentSim.getRoom().removeFurniture(x, y));        
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

    public void upgradeHouseOption() {
        synchronized(upgradeLock) {
            if (isUpgrading) {
                throw new IllegalArgumentException("House still in upgrade.");
            }
            isUpgrading = true;
        }

        System.out.println("\nHouse Map:");
        currentSim.getHouse().printHouse();

        System.out.print("\nENTER NEW ROOM NAME: ");
        String roomName = scan.nextLine();

        System.out.print("ENTER ROOM AS BENCHMARK: ");
        String benchmark = scan.nextLine();
        Room benchmarkRoom = null;
        
        for (Room room : currentSim.getHouse().getRooms()) {
            if (room.getRoomName().equals(benchmark)) {
                benchmarkRoom = room;
                break;
            }
        }

        if (benchmarkRoom == null) {
            synchronized(upgradeLock) {
                isUpgrading = false;
            }
            throw new IllegalArgumentException("Benchmark room not found.");
        }
        
        System.out.println("Direction : (N)orth  (S)outh  (E)ast  (W)est");
        System.out.print("ENTER DIRECTION BASED ON BENCHMARK ROOM: ");
        String direction = scan.nextLine();
        Direction dir = null;
        
        if (direction.equals("N")) {
            dir = Direction.NORTH;
        } else if (direction.equals("S")) {
            dir = Direction.SOUTH;
        } else if (direction.equals("E")) {
            dir = Direction.EAST;
        } else if (direction.equals("W")) {
            dir = Direction.WEST;
        } else {
            synchronized(upgradeLock) {
                isUpgrading = false;
            }
            throw new IllegalArgumentException("Invalid direction.");
        }

        Room target = benchmarkRoom;
        Direction targetDir = dir;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(18 * 60 * 1000);
                    currentSim.getHouse().addRoom(roomName, target, targetDir);
                    currentSim.setMoney(currentSim.getMoney() - 1500);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    synchronized(upgradeLock) {
                        isUpgrading = false;
                    }
                }
            }
        }).start();
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
}
