package main.menu;

import main.Game;
import map.Direction;
import map.Room;
import util.Input;

public class UpgradeHouse implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        if (gm.getCurrentSim().getUpgradeHouse() != null) {
            throw new IllegalArgumentException("Rumah sedang di-upgrade!");
        }

        if (gm.getCurrentSim().getMoney() >= 1500) {
            gm.getCurrentSim().getHouse().printHouse();

            System.out.print("\nMASUKKAN NAMA RUANGAN BARU : ");
            String roomName = scan.nextLine();
            boolean getInput = true;

            while (getInput) {
                if (roomName.length() > 21) {
                    System.out.println("\nNama ruangan terlalu panjang!");
                    System.out.print("\nMASUKKAN NAMA RUANGAN BARU : ");
                    roomName = scan.nextLine();
                } else {
                    getInput = false;
                }
            }

            for (Room room: gm.getCurrentSim().getHouse().getRooms()) {
                if (room.getRoomName().equals(roomName)){
                    throw new IllegalArgumentException("Nama ruangan sudah ada!");
                }
            }

            System.out.print("MASUKKAN NAMA RUANGAN TARGET : ");
            String target = scan.nextLine();
            
            Room targetRoom = null;
            for (Room room : gm.getCurrentSim().getHouse().getRooms()) {
                if (room.getRoomName().equals(target)) {
                    targetRoom = room;
                    break;
                }
            }
            if (targetRoom == null) {
                throw new IllegalArgumentException("\nRuangan target tidak ditemukan!");
            }
            
            System.out.println("\nDirection : (N)orth  (S)outh  (E)ast  (W)est");
            System.out.print("MASUKKAN ARAH RUANGAN BERDASARKAN TARGET : ");
            Direction direction = Direction.strToDirection(scan.nextLine());

            if (targetRoom.getConnectedRooms().get(direction) != null) {
                throw new IllegalArgumentException("\nAda ruangan lain!");
            }            
            gm.getCurrentSim().upgradeHouse(roomName, targetRoom, direction);
            System.out.println("\nMelakukan upgrade....");
        } else {
            System.out.println("\nUang Sim tidak mencukupi!");
            scan.enterUntukLanjut();
        }
    }
}
