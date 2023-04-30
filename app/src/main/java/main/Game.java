package main;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import entity.Sim;
import map.Renderable;
import map.World;
import util.ClearScreen;
import util.Input;

public class Game {
    private Renderable currentView;
    private Sim currentSim;
    private World world;
    private static long gameTime;
    Input scan = Input.getInstance();

    public Game() {
        gameTime = 0;
        world = World.getInstance();
        startNew();
    }

    public void showGamePanel() {
        ClearScreen.clear();
        showRender();
        showOptions();
        getInput();
    }

    public void showRender() {
        char[][] rendered = currentView.render();
        for (int i=0; i<6; i++) {
            for (int j=0; j<6; j++) {
                System.out.print(rendered[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void showOptions() {
        System.out.println("\n(S)im Info  (I)nventory  (A)ctions  (B)uy Item  (M)ove Sim");
        System.out.println("(C)reate Sim  (Ch)ange Sim  (E)dit Room");
        System.out.println("(U)pgrade House");
    }

    public void getInput() {
        System.out.print("ENTER COMMAND: ");
        String input = scan.next();

        if (input.equals("S")) {
            showSimInfo(currentSim);
        }
    }

    public void actionOptions() {
        // if (oprion == S) scan.line();
    }

    public void showSimInfo(Sim s) {

    }

    public void startNew() {
        // System.out.print("ENTER SIM NAME: ");
        // String simName = scan.next();
        String simName = "ABil";
        boolean found = false;
        while (!found) {
            try {
                world.addHouse(ThreadLocalRandom.current().nextInt(0, 64 + 1), ThreadLocalRandom.current().nextInt(0, 64 + 1), simName);
                found = true;
            } catch (Exception e) {
                
            }
        }
        currentSim = world.getSims().get(0);
        currentView = currentSim.getRoom();
    }

    public void changeView(Renderable view) {
        currentView = view;
    }

    public static void moveTime(int time) {
        for (int i=0; i<time; i++) {
            try {
                Thread.sleep(1000); // 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }    
        }
    }

    public static long getTime() {return gameTime;}
}
