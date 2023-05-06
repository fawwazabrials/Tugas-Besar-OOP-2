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

        if (gm.getCurrentSim().isUpgradingHouse()) 
            System.out.println(String.format("Tersisa waktu %s sampai rumah selesai di-upgrade", 18*60 - (gm.getClock().getGameTime() - gm.getCurrentSim().getTimeUpgradeHouse())));        
        
        if (gm.getCurrentSim().isShoppingQueue())
            System.out.println(String.format("Tersisa waktu %s sampai barang yang dibeli sampai", Angka.secToTime(gm.getCurrentSim().getDeliveryTime() - (gm.getClock().getGameTime() - gm.getCurrentSim().getTimeShopQueue()))));

        scan.enterUntukLanjut();
    }

}
