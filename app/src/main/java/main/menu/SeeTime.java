package main.menu;

import main.Game;
import util.Input;

public class SeeTime implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
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

}
