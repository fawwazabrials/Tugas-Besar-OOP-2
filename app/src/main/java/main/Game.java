package main;

import java.util.ArrayList;
import java.util.List;

import entity.*;
import exception.SimIsDeadException;
import item.*;
import main.menu.*;
import map.*;
import util.*;

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
    Input scan = Input.getInstance();


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
        currentHouse = currentSim.getCurrHouse().getOwnerName();
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

        removeAllDeadSim();
        if (currentSim.isDead()) {
            showDeadScreen();
        }

        renderCurrentView();
        menu.askOverlapAction();
        menu.showOptions();
        menu.getInput();
    }

    public void showDeadScreen() {
        System.out.println("\nSim kamu mati!");

        if (world.getSims().size() == 0) {
            showGameOverScreen();
        } else {
            menu.executeOption("Ch");;
        }
    }

    public void showGameOverScreen() {
        System.out.println("Semua Sim kamu mati! Kamu kalah!");
        System.exit(0);
    }

    public void renderCurrentView() {
        char[][] rendered = currentView.render();
    
        rendered[currentSim.getY()][currentSim.getX()] = 'S'; // tampilin sim
        System.out.println("Rumah " + currentHouse + " - " + currentSim.getRoom().getRoomName());
    
        System.out.println("  0 1 2 3 4 5");

        for (int i=0; i<6; i++) {
            System.out.print(i + " ");
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

    public void removeAllDeadSim() {
        List<String> deadSims = new ArrayList<String>();
        List<Sim> clone = new ArrayList<Sim>(getWorld().getSims());

        for (Sim s : clone) {
            if (s.isDead()) {
                s.killSim();
                deadSims.add(s.getName());
                getWorld().removeHouse(s.getHouse().getX(), s.getHouse().getY());

                if (s.getHouse() == currentSim.getCurrHouse()) {
                    try {
                        System.out.println("Karena sim yang dikunjungin udah mati, sim akan pergi kembali ke rumahnya.");
                        currentSim.visit(currentSim);
                        scan.enterUntukLanjut();
                    } catch (SimIsDeadException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        if (deadSims.size() > 0) {
            System.out.println(String.format("Sim yang mati %s", deadSims));
        }
    }
}