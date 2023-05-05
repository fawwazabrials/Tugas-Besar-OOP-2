// package main;

// import java.util.List;
// import java.util.InputMismatchException;

// import entity.Sim;
// import exception.NoInputException;
// import map.Direction;
// import map.Room;
// import util.Angka;
// import util.Input;

// public class SimMenuOption {
//     Input scan = Input.getInstance();
//     Game gm;

//     public SimMenuOption(Game gm) {
//         this.gm = gm;
//     }

    

//     public void eat() {
//         // TODO: Implement ini

//     }

//     public void cook() {
//         // TODO: Implement ini
//     }

    

    

//     public void stargaze() {
//         // TODO: Implement ini

//     }

//     public void seeTime() {
        
//     }

    

//     public void upgradeHouse() {
//         if (game.currentSim.getUpgradeHouse() != null) {
//             throw new IllegalArgumentException("Rumah sedang di-upgrade!");
//         }

//         if (game.currentSim.getMoney() >= 1500) {
//             game.currentSim.getHouse().printHouse();

//             System.out.print("\nMASUKKAN NAMA RUANGAN BARU : ");
//             String roomName = scan.nextLine();
//             for (Room room: game.currentSim.getHouse().getRooms()) {
//                 if (room.getRoomName().equals(roomName)){
//                     throw new IllegalArgumentException("Nama ruangan sudah ada!");
//                 }
//             }

//             System.out.print("MASUKKAN NAMA RUANGAN TARGET : ");
//             String target = scan.nextLine();
            
//             Room targetRoom = null;
//             for (Room room : game.currentSim.getHouse().getRooms()) {
//                 if (room.getRoomName().equals(target)) {
//                     targetRoom = room;
//                     break;
//                 }
//             }
//             if (targetRoom == null) {
//                 throw new IllegalArgumentException("\nRuangan target tidak ditemukan!");
//             }
            
//             System.out.println("\nDirection : (N)orth  (S)outh  (E)ast  (W)est");
//             System.out.print("MASUKKAN ARAH RUANGAN BERDASARKAN TARGET : ");
//             Direction direction = Direction.strToDirection(scan.nextLine());

//             if (targetRoom.getConnectedRooms().get(direction) != null) {
//                 throw new IllegalArgumentException("\nAda ruangan lain!");
//             }            
//             game.currentSim.upgradeHouse(roomName, targetRoom, direction);
//             System.out.println("\nMelakukan upgrade....");
//         } else {
//             System.out.println("\nUang Sim tidak mencukupi!");
//             scan.enterUntukLanjut();
//         }
//     }
// }
