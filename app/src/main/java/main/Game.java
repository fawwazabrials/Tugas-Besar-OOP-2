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
    private static long gameTime, day;

    private long dayLastSimAdded = -2;
    Input scan = Input.getInstance();

    public Game() {
        gameTime = 0;
        day = 0;
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
        System.out.println("\n(S)im Info        (C)urrent Location  (I)nventory");
        System.out.println("(U)pgrade House   (M)ove Sim          (E)dit Room");
        System.out.println("(A)dd Sim         (Ch)ange Sim        (L)ist Object  ");
        System.out.println("(G)o to Object    (A)ction            (S)hop");
        System.out.println("                 E(X)it");
    }

    public void getInput() {
        System.out.print("\nENTER COMMAND: ");
        String input = scan.next();

        if (input.equals("Ch")) {
            changeSimOption();
        } 
        else if (input.equals("A")) {
            addSimOption();
        }
        else if (input.equals("X")) {
            System.exit(0);
        }
    }

    public void actionOptions() {
        // if (oprion == S) scan.line();
    }

    public void changeSimOption() {
        int simNum = world.getSims().size();

        if (simNum <= 1) {
            System.out.println("Kamu hanya memiliki 1 sim! Silahkan buat sim lain untuk dimainkan.");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nPilih sim mana yang ingin dimainkan: ");
            int cnt = 1;
            for (int i=0; i<simNum; i++) {
                if (!world.getSims().get(0).equals(currentSim)) { // Bukan sim yang sekarang lagi dimainin
                    System.out.println(cnt + ". " + world.getSims().get(i).getName());
                } else {
                    System.out.println(cnt + ". " + world.getSims().get(i).getName() + " (Sim sekarang)");
                }
                cnt++;
            }

            int input = -1;
            while (input == -1) {
                System.out.println("PILIH SIM: ");
                if (input <= 0 || input > simNum) {
                    System.out.println("Masukan angka di dalam batas sim!");
                    input = -1;
                } else {
                    if (world.getSims().get(input-1).equals(currentSim)) {
                        System.out.println("Tidak bisa mengganti ke sim yang sedang dimainkan!");
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
            scan.enterUntukLanjut();
        } else {
            System.out.print("ENTER SIM NAME: ");
            String simName = scan.next();

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
            dayLastSimAdded = day;
        }
    }

    public void showSimInfo(Sim s) {
        
    }

    public void startNew() {
        addSimOption();
    }

    public void changeSim(Sim newSim) {
        currentSim = newSim;
        changeView(currentSim.getRoom());
    }

    public void changeView(Renderable newView) {
        currentView = newView;
    }

    public static void moveTime(int time) {
        for (int i=0; i<time; i++) {
            try {
                Thread.sleep(1000); // 1 second
                day = gameTime % 720000;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }    
        }
    }

    public static long getTime() {return gameTime;}
}
