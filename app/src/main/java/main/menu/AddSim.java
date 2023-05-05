package main.menu;

import exception.NoInputException;
import main.Game;
import util.Angka;
import util.Input;

public class AddSim implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        if (gm.getClock().getDay() <= gm.getDayLastSimAdded() && !gm.getCheat().isAddinfinitesim()) {
            System.out.println("\nSim tidak bisa ditambahkan! Sim hanya bisa ditambahkan setiap 1 hari.");
            scan.enterUntukLanjut();
        } 
        
        else {
            String simName = "";
            boolean getInput = true;
        
            while (getInput) {
                try {
                    simName = scan.getInput("ENTER SIM NAME: ");
                    getInput = false;
                } catch (NoInputException e) {
                    System.out.println("Nama sim tidak bisa kosong");
                }
            }

            boolean found = false;
            while (!found) {
                try {
                    gm.getWorld().addHouse(Angka.randint(0, 64 + 1), Angka.randint(0, 64 + 1), simName);
                    found = true;
                } catch (Exception e) {
                    
                }
            }

            gm.setDayLastSimAdded(gm.getClock().getDay());
        }
    }   
}
