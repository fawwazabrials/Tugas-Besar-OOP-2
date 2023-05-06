package main.menu;

import main.Game;
import util.Angka;
import util.Input;

public class SeeTime implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        System.out.println(String.format("Hari: %s", gm.getClock().getDay()));
        System.out.println(String.format("Jam: %s", gm.getClock().getDayTime()));

        System.out.println(String.format("\nSim terakhir kali makan %s lalu", Angka.secToTime(gm.getClock().getGameTime() - gm.getCurrentSim().getTimeLastEat())));
        System.out.println(String.format("Sim terakhir kali tidur %s lalu\n", Angka.secToTime(gm.getClock().getGameTime() - gm.getCurrentSim().getTimeLastSleep())));

        if (gm.getCurrentSim().isUpgradingHouse()) 
            System.out.println(String.format("Tersisa waktu %s sampai rumah selesai di-upgrade", Angka.secToTime(18*60 - (gm.getClock().getGameTime() - gm.getCurrentSim().getTimeUpgradeHouse()))));        
        else System.out.println("Sim tidak sedang upgrade rumah.");

        if (gm.getCurrentSim().isShoppingQueue())
            System.out.println(String.format("Tersisa waktu %s sampai barang yang dibeli sampai", Angka.secToTime(gm.getCurrentSim().getDeliveryTime() - (gm.getClock().getGameTime() - gm.getCurrentSim().getTimeShopQueue()))));
        else System.out.println("Sim tidak sedang membeli barang.");


        scan.enterUntukLanjut();
    }

}
