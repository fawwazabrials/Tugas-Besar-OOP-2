package main;

import entity.*;
import util.*;
import item.*;
import main.menu.*;
import map.*;
import java.util.*;

public class Game {

    private Input scan = Input.getInstance();

    // IMPORTANT GAME ATTRIBUTES
    private Renderable currentView;
    private String currentHouse;
    private Sim currentSim;
    private World world;
    private Clock clock;
    
    private Action action = new Action(this);
    private Menu menu = new Menu(this);
    private Cheat cheat = new Cheat(this);

    public Action getAction() {return action;}
    public Cheat getCheat() {return cheat;}
    public Clock getClock() {return clock;}
    public World getWorld() {return world;}
    public void setWorld(World world) {this.world = world;}
    public void setCurrentView(Renderable currentView) {this.currentView = currentView;}
    public Renderable getCurrentView() {return currentView;}
    public void setCurrentSim(Sim currentSim) {this.currentSim = currentSim;}
    public Sim getCurrentSim() {return currentSim;}
    public void setCurrentHouse(String currentHouse) {this.currentHouse = currentHouse;}
    public String getCurrentHouse() {return currentHouse;}

    public void changeSim(Sim sim) {
        currentSim = sim;
        changeView(currentSim.getRoom());
        currentHouse = currentSim.getName();
    }

    public void moveSim() {

    }

    // GAME CONFIG
    private int dayLastSimAdded = -1;
    private boolean overlapActionShowed;

    public boolean isOverlapActionShowed() {return overlapActionShowed;}
    public void setOverlapActionShowed(boolean overlapActionShowed) {this.overlapActionShowed = overlapActionShowed;}
    public int getDayLastSimAdded() {return dayLastSimAdded;}
    public void setDayLastSimAdded(int dayLastSimAdded) {this.dayLastSimAdded = dayLastSimAdded;}
    
    public Furniture getOverlapFurniture() {
        return currentSim.getRoom().getRoomGrid()[currentSim.getY()][currentSim.getX()];
    }

    // DEFAULT constructor; Start new game
    public Game() {
        clock = new Clock(this);
        world = World.getInstance(this);

        // create new game
        menu.executeOption("Ad");
        currentSim = world.getSims().get(0);
        currentView = currentSim.getRoom();
        currentHouse = currentSim.getName();
        overlapActionShowed = false;
    }

    public void showGamePanel() {
        // TODO: ini bawah nanti di uncomment
        // ClearScreen.clear();

        // if (currentSim.isDead()) {
        //     currentSim.killSim();
        //     world.getSims().remove(currentSim);
        //     showDeadScreen();
        // }

        renderCurrentView();
        menu.askOverlapAction();
        menu.showOptions();
        menu.getInput();
    }

    // public void showDeadScreen() {
    //     System.out.println("\nSim kamu mati!");

    //     if (world.getSims().size() == 0) {
    //         showGameOverScreen();
    //     } else {
    //         menu.;
    //     }
    // }

    // public void showGameOverScreen() {
    //     System.out.println("Semua Sim kamu mati! Kamu kalah!");
    //     System.exit(0);
    // }

    public void renderCurrentView() {
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

    public void changeView(Renderable newView) {
        currentView = newView;
    }

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
}
