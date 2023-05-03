package main;

import java.util.List;
import java.util.InputMismatchException;

import entity.Sim;
import exception.NoInputException;
import util.Angka;
import util.Input;

public class GameSimOption {
    Game game;
    Input scan = Input.getInstance();

    public GameSimOption(Game game) {
        this.game = game;
    }

    public void sleep() {
        // TODO: Implement ini

    }

    public void eat() {
        // TODO: Implement ini

    }

    public void cook() {
        // TODO: Implement ini
    }

    public void work() {
        // TODO: Implement ini

    }

    public void visit() {
        List<Sim> sims = game.world.getSims();
        int simNum = sims.size();
        int houseNow = 0;


        if (simNum <= 1) {
            System.out.println("\nTidak ada sim lain! Silahkan buat sim terlebih dahulu!");
            scan.enterUntukLanjut();
        } else {
            System.out.println("\nSim yang bisa dikunjungi: ");
            for (int i=0; i<simNum; i++) {
                System.out.print((i+1) + ". " + sims.get(i).getName() + " (" + sims.get(i).getHouse().getX() + ", " + sims.get(i).getHouse().getY() + ")");
                if (game.currentSim.getCurrHouse() == sims.get(i).getHouse()) { // sim sekarang lagi ada dirumah mana
                    System.out.println(" (Lokasi sekarang)");
                    houseNow = i;
                } else {
                    System.out.println("");
                }
            }

            int input;
            boolean getInput = true;

            while (getInput) {
                try {
                    input = scan.getIntegerInput("MASUKKAN SIM YANG INGIN DIKUNJUNGI: ");

                    if (input <= 0 || input > simNum) {
                        System.out.println("Masukkan angka dalam batas sim! Silahkan coba ulang.");
                        scan.enterUntukLanjut();
                    } else if (input == (houseNow+1)) {
                        System.out.println("Sim tidak bisa mengujungi lokasi dia sekarang! Silahkan coba ulang.");
                        scan.enterUntukLanjut();
                    } else {
                        System.out.println("\nSim sedang berjalan untuk berkunjung...");
        
                        game.currentSim.visit(sims.get(input-1));
                        game.currentHouse = sims.get(input-1).getName();
        
                        System.out.println("\nSim sudah sampai!");
                    }
                }
                catch (NoInputException e) {
                    getInput = false;
                }
                catch (InputMismatchException  e) {
                    System.out.println("Masukkan angka!");
                }
            }
        }


    }

    public void stargaze() {
        // TODO: Implement ini

    }

    public void workout() {
        int time = -999;

        while (time <= 0) {
            System.out.print("\nMASUKKAN WAKTU (dalam kelipatan 20 detik): ");
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