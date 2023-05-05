package main.menu;

import main.Game;
import util.Input;

public class SimInfo implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        System.out.println("\n------ SIM INFO ------");
        System.out.println("Nama: " + gm.getCurrentSim().getName());
        System.out.println("Pekerjaan: " + gm.getCurrentSim().getJobName());
        System.out.println("Kesehatan: " + gm.getCurrentSim().getHealth());
        System.out.println("Kekenyangan: " + gm.getCurrentSim().getHunger());
        System.out.println("Mood: " + gm.getCurrentSim().getMood());
        System.out.println("Uang: " + gm.getCurrentSim().getMoney());
        scan.enterUntukLanjut();
    }

}
