package map;
import java.util.*;
import java.awt.Point;

public class House implements HouseAction{
    // House coordinate
    private int x;
    private int y;
    private List<Room> rooms;
    private Map<Point, Room> roomMap;

    public House(int x, int y) {
        this.x = x;
        this.y = y;
        rooms = new ArrayList<Room>();
        rooms.add(new Room("Bed Room"));
        roomMap = new HashMap<>();
        roomMap.put(new Point(0, 0), rooms.get(0));
    }

    // Getter Methods
    public int getX() {return x;}
    public int getY() {return y;}
    public List<Room> getRooms() {return rooms;}

    public void addRoom(String roomName, Room benchmarkRoom, Direction direction) {
        if (benchmarkRoom.getConnectedRooms().get(direction) != null) {
            throw new IllegalArgumentException("Room already exists!");
        }
        /* Mencari key dengan value benchamarkRoom */
        Point coordinate = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(benchmarkRoom)) {
                coordinate = entry.getKey();
            }
        }
        if (coordinate == null) {
            throw new IllegalArgumentException("Room not found!");
        }
        Room newRoom = new Room(roomName);
        benchmarkRoom.getConnectedRooms().put(direction, newRoom);
        switch(direction) {
            case NORTH:
                roomMap.put(new Point((int)coordinate.getX() - 1, (int)coordinate.getY()), newRoom);
                break;
            case SOUTH:
                roomMap.put(new Point((int)coordinate.getX() + 1, (int)coordinate.getY()), newRoom);
                break;
            case EAST:
                roomMap.put(new Point((int)coordinate.getX(), (int)coordinate.getY() + 1), newRoom);
                break;
            case WEST:
                roomMap.put(new Point((int)coordinate.getX(), (int)coordinate.getY() - 1), newRoom);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

        /* Menambahkan Room yang bersebelahan dengan newRoom */
        Point newRoomCoordinate = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(newRoom)) {
                newRoomCoordinate = entry.getKey();
                break;
            }
        }

        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX() - 1, (int)newRoomCoordinate.getY()))) {
            newRoom.getConnectedRooms().put(Direction.SOUTH, roomMap.get(new Point((int)newRoomCoordinate.getX() - 1, (int)newRoomCoordinate.getY())));
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX() + 1, (int)newRoomCoordinate.getY()))) {
            newRoom.getConnectedRooms().put(Direction.NORTH, roomMap.get(new Point((int)newRoomCoordinate.getX() + 1, (int)newRoomCoordinate.getY())));
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() - 1))) {
            newRoom.getConnectedRooms().put(Direction.WEST, roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() - 1)));
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() + 1))) {
            newRoom.getConnectedRooms().put(Direction.EAST, roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() + 1)));
        }

        rooms.add(newRoom);
    }

    public void removeRoom(Room room) {
        Point coordinate = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(room)) {
                coordinate = entry.getKey();
            }
        }
        if (coordinate == null) {
            throw new IllegalArgumentException("Room not found!");
        }

        
        /*Menghapus setiap hubungan ruangan yang terhubung dengan Room yang ingin dihapus */
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            for (Map.Entry<Direction, Room> connectedRoom : entry.getValue().getConnectedRooms().entrySet()) {
                if (connectedRoom.getValue() != null && connectedRoom.getValue().equals(room)) {
                    entry.getValue().getConnectedRooms().remove(connectedRoom.getKey());
                }
            }
        }
        roomMap.remove(coordinate);
        rooms.remove(room);
    }
    
    public void moveRoom(Room room, Room benchmarkRoom, Direction direction) {
        if (room.equals(benchmarkRoom)) {
            throw new IllegalArgumentException("Room and benchmark room cannot be the same!");
        }

        Point roomCoord = null;
        Point benchCoord = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(room)) {
                roomCoord = entry.getKey();
            }
            if (entry.getValue().equals(benchmarkRoom)) {
                benchCoord = entry.getKey();
            }
            if (roomCoord != null && benchCoord != null) {
                break;
            }
        }

        // Cek apakah koordinat sudah ditemukan
        if (roomCoord == null) {
            throw new IllegalArgumentException("Room not found!");
        }
        if (benchCoord == null) {
            throw new IllegalArgumentException("Benchmark room not found!");
        }

        // Cari koordinat untuk menambahkan ruangan yang baru
        Point newCoord = null;
        switch (direction) {
            case NORTH:
                newCoord = new Point((int)benchCoord.getX() - 1, (int)benchCoord.getY());
                break;
            case SOUTH:
                newCoord = new Point((int)benchCoord.getX() + 1, (int)benchCoord.getY());
                break;
            case EAST:
                newCoord = new Point((int)benchCoord.getX(), (int)benchCoord.getY() + 1);
                break;
            case WEST:
                newCoord = new Point((int)benchCoord.getX(), (int)benchCoord.getY() - 1);
                break;
        }

        // Cek apakah koordinat baru sudah ada ruangan atau tidak
        if (roomMap.containsKey(newCoord)) {
            throw new IllegalArgumentException("Room already exists!");
        }
        
        // Hapus koneksi ruangan yang ingin dipindahkan dengan ruangan sekitarnya
        for (Map.Entry<Direction, Room> entry : room.getConnectedRooms().entrySet()) {
            entry.setValue(null);
        }

        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            for (Map.Entry<Direction, Room> connectedRoom : entry.getValue().getConnectedRooms().entrySet()) {
                if (connectedRoom.getValue() != null && connectedRoom.getValue().equals(room)) {
                    entry.getValue().getConnectedRooms().remove(connectedRoom.getKey());
                }
            }
        }

        // Hapus ruangan yang ingin dipindahkan dari koordinat lamanya
        roomMap.remove(roomCoord);

        // Tambahkan ruangan yang ingin dipindahkan ke koordinat baru
        roomMap.put(newCoord, room);

        if (roomMap.containsKey(new Point((int)newCoord.getX() - 1, (int)newCoord.getY()))) {
            room.getConnectedRooms().put(Direction.SOUTH, roomMap.get(new Point((int)newCoord.getX() - 1, (int)newCoord.getY())));
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX() + 1, (int)newCoord.getY()))) {
            room.getConnectedRooms().put(Direction.NORTH, roomMap.get(new Point((int)newCoord.getX() + 1, (int)newCoord.getY())));
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX(), (int)newCoord.getY() - 1))) {
            room.getConnectedRooms().put(Direction.WEST, roomMap.get(new Point((int)newCoord.getX(), (int)newCoord.getY() - 1)));
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX(), (int)newCoord.getY() + 1))) {
            room.getConnectedRooms().put(Direction.EAST, roomMap.get(new Point((int)newCoord.getX(), (int)newCoord.getY() + 1)));
        }
    }

    // public void printHouse() {
    //     int minX = 0;
    //     int minY = 0;
    //     int maxX = 0;
    //     int maxY = 0;
    //     for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
    //         if (entry.getKey().getX() < minX) {
    //             minX = (int)entry.getKey().getX();
    //         }
    //         if (entry.getKey().getX() > maxX) {
    //             maxX = (int)entry.getKey().getX();
    //         }
    //         if (entry.getKey().getY() < minY) {
    //             minY = (int)entry.getKey().getY();
    //         }
    //         if (entry.getKey().getY() > maxY) {
    //             maxY = (int)entry.getKey().getY();
    //         }
    //     }
    //     for (int i = minX; i <= maxX; i++) {
    //         for (int j = minY; j <= maxY; j++) {
    //             if (roomMap.containsKey(new Point(i, j))) {
    //                 System.out.print(roomMap.get(new Point(i, j)).getRoomName() + " ");
    //             } else {
    //                 System.out.print("X ");
    //             }
    //         }
    //         System.out.println();
    //     }
    // }
}
