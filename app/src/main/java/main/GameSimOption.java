package main;

import util.Angka;
import util.Input;

public class GameSimOption {
    Game game;
    Input scan = Input.getInstance();

    public GameSimOption(Game game) {
        this.game = game;
    }

    public void workout() {
        int time = -999;

        while (time <= 0) {
            System.out.println("\nMASUKKAN WAKTU (dalam kelipatan 20 detik): ");
            time = Angka.stringToInt(scan.nextLine());
            if (time % 20 == 0) {
                System.out.println("\nSim sedang berolahraga...");
                game.currentSim.workout(time * 1000);
                System.out.println("\nSim selesai berolahraga!");
                scan.enterUntukLanjut();
            } else {
                System.out.println("Masukan waktu dalam kelipatan 20 detik!");
                time = -999;
            }
        }
    }

    public void seeTime() {
        long secInDay = (Game.getTime() - Game.getDay()*720)*2;
        int hour = (int)secInDay / 60;
        int minute = (int)secInDay % 60;
        // System.out.println(secInDay);
        // System.out.println(Game.getDay());
        // System.out.println(Game.getTime());

        String showHour, showMinute;
        if ((double)hour / 10 < 1) {
            showHour = "0" + ((Integer)hour).toString();
        } else showHour = ((Integer)hour).toString();

        if ((double)minute / 10 < 1) {
            showMinute = "0" + ((Integer)minute).toString();
        } else showMinute = ((Integer)minute).toString();

        System.out.println("\nWaktu sekarang adalah hari ke-" + (Game.getDay()+1) + " jam " + showHour + "." + showMinute);
        scan.enterUntukLanjut();
    }

    public void poop() {
        // 1 siklus = 10 detik
        System.out.println("\nSim sedang buang air...");
        game.currentSim.poop(10000); // 10 DETIK
        System.out.println("\nSim selesai buang air!");
        scan.enterUntukLanjut();
    }
}
