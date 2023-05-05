package main;

import entity.*;
import item.*;
import main.menu.*;
import map.*;

public class Game {
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
        // TODO
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

    public void showDishTable() {
        System.out.println("\n    List of Dish:");
        System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Dish Name", "Hunger Point", "Recipe"));
        System.out.println(String.format("|%s|", "--------------------------------------------------------------------"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Nasi Ayam", "16", "Nasi, Ayam"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Nasi Kari", "30", "Nasi, Kentang, Wortel, Sapi"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Susu Kacang", "5", "Susu, Kacang"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Tumis Sayur", "5", "Wortel, Bayam"));
        System.out.println(String.format("| %-15s | %-15s | %-30s |", "Bistik", "22", "Kentang, Sapi"));
        System.out.println(String.format(" %s ", "--------------------------------------------------------------------"));
    }
}