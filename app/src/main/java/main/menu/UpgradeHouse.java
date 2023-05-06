package main.menu;

import exception.NoInputException;
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

            String roomName = "";
            String target = "";
            boolean getInput = true;
            while (getInput) {
                try {
                    roomName = scan.getInput("\nMasukkan nama ruangan baru: ");
                    boolean isExist = false;
                    for (Room room: gm.getCurrentSim().getHouse().getRooms()) {
                        if (room.getRoomName().equals(roomName)){
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        if (roomName.length() <= 21) {
                            target = scan.getInput("\nMasukkan nama ruangan target: ");
                            if (target.length() <= 21) {
                                getInput = false;
                            } else {
                                System.out.println("\nNama ruangan target terlalu panjang!");
                            }
                        } else {
                            System.out.println("\nNama ruangan terlalu panjang!");
                        }
                    } else {
                        System.out.println("\nNama ruangan sudah ada!");
                    }

                } catch (NoInputException e) {
                    getInput = false;
                    return;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    scan.enterUntukLanjut();
                }
            }

            for (Room room: gm.getCurrentSim().getHouse().getRooms()) {
                if (room.getRoomName().equals(roomName)){
                    throw new IllegalArgumentException("Nama ruangan sudah ada!");
                }
            }

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
            
            Direction direction = null;

            String directionStr = "";
            getInput = true;
            try {
                while (getInput) {
                    System.out.println("\nDirection : (N)orth  (S)outh  (E)ast  (W)est");
                    directionStr = scan.getInput("\nMasukkan arah ruangan baru: ");
                    if (directionStr.length() <= 1) {
                        direction = Direction.strToDirection(directionStr);
                        getInput = false;
                    } else {
                        System.out.println("\nMasukkan arah yang benar!");
                    }
                }
            } catch (NoInputException e) {
                getInput = false;
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scan.enterUntukLanjut();
            }

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
