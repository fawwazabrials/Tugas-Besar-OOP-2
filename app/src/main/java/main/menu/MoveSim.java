package main.menu;

import main.Game;
import map.Room;
import util.Input;

public class MoveSim implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        if (gm.getCurrentHouse() == null) {
            System.out.println("Sim sedang tidak di dalam rumah.");
            scan.enterUntukLanjut();
            return;
        }

        System.out.println("\nHouse Map :");
        System.out.println("==========================");
        gm.currentHouse().printHouse();
        

        System.out.print("ENTER ROOM NAME: ");
        String roomName = scan.nextLine();
        Room room = null;
        for (Room r : gm.currentHouse().getRooms()) {
            if (r.getRoomName().equals(roomName)) {
                room = r;
            }
        }

        if (room == null) {
            System.out.println("Ruangan tidak ditemukan!");
            scan.enterUntukLanjut();
            return;
        }

        gm.getCurrentSim().move(room);
        gm.changeView(gm.getCurrentSim().getRoom());
    }
}
