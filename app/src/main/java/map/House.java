package map;
import java.util.*;
import java.awt.Point;

public class House{
    // House coordinate
    private int x;
    private int y;
    private String ownerName;
    private List<Room> rooms;
    private Map<Point, Room> roomMap;

    public House(int x, int y, String ownerName) {
        this.x = x;
        this.y = y;
        this.ownerName = ownerName;
        rooms = new ArrayList<Room>();
        rooms.add(Room.makeDefaultRoom());
        roomMap = new HashMap<>();
        roomMap.put(new Point(0, 0), rooms.get(0));
    }

    // Getter Methods
    public int getX() {return x;}
    public int getY() {return y;}
    public String getOwnerName() {return ownerName;}
    public List<Room> getRooms() {return rooms;}

    public void addRoom(String roomName, Room benchmarkRoom, Direction direction) {
        /* Mencari key dengan value benchamarkRoom */
        Point coordinate = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(benchmarkRoom)) {
                coordinate = entry.getKey();
            }
        }

        Room newRoom = new Room(roomName);
        benchmarkRoom.getConnectedRooms().put(direction, newRoom.getRoomName());
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
        }

        Point newRoomCoordinate = null;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getValue().equals(newRoom)) {
                newRoomCoordinate = entry.getKey();
                break;
            }
        }

        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX() - 1, (int)newRoomCoordinate.getY()))) {
            newRoom.getConnectedRooms().put(Direction.NORTH, roomMap.get(new Point((int)newRoomCoordinate.getX() - 1, (int)newRoomCoordinate.getY())).getRoomName());
            roomMap.get(new Point((int)newRoomCoordinate.getX() - 1, (int)newRoomCoordinate.getY())).getConnectedRooms().put(Direction.SOUTH, newRoom.getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX() + 1, (int)newRoomCoordinate.getY()))) {
            newRoom.getConnectedRooms().put(Direction.SOUTH, roomMap.get(new Point((int)newRoomCoordinate.getX() + 1, (int)newRoomCoordinate.getY())).getRoomName());
            roomMap.get(new Point((int)newRoomCoordinate.getX() + 1, (int)newRoomCoordinate.getY())).getConnectedRooms().put(Direction.NORTH, newRoom.getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() - 1))) {
            newRoom.getConnectedRooms().put(Direction.WEST, roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() - 1)).getRoomName());
            roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() - 1)).getConnectedRooms().put(Direction.EAST, newRoom.getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() + 1))) {
            newRoom.getConnectedRooms().put(Direction.EAST, roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() + 1)).getRoomName());
            roomMap.get(new Point((int)newRoomCoordinate.getX(), (int)newRoomCoordinate.getY() + 1)).getConnectedRooms().put(Direction.WEST, newRoom.getRoomName());
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
            throw new IllegalArgumentException("\nRuangan tidak ditemukan!");
        }

        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            for (Map.Entry<Direction, String> connectedRoom : entry.getValue().getConnectedRooms().entrySet()) {
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
            throw new IllegalArgumentException("\nRoom and benchmark room cannot be the same!");
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

        if (roomCoord == null) {
            throw new IllegalArgumentException("\nRuangan tidak ditemukan!");
        }
        if (benchCoord == null) {
            throw new IllegalArgumentException("\nRuangan target tidak ditemukan!");
        }

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

        if (roomMap.containsKey(newCoord)) {
            throw new IllegalArgumentException("\nSudah ada ruangan lain!");
        }
        
        for (Map.Entry<Direction, String> entry : room.getConnectedRooms().entrySet()) {
            entry.setValue(null);
        }

        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            for (Map.Entry<Direction, String> connectedRoom : entry.getValue().getConnectedRooms().entrySet()) {
                if (connectedRoom.getValue() != null && connectedRoom.getValue().equals(room)) {
                    entry.getValue().getConnectedRooms().remove(connectedRoom.getKey());
                }
            }
        }

        roomMap.remove(roomCoord);

        roomMap.put(newCoord, room);

        if (roomMap.containsKey(new Point((int)newCoord.getX() - 1, (int)newCoord.getY()))) {
            room.getConnectedRooms().put(Direction.SOUTH, roomMap.get(new Point((int)newCoord.getX() - 1, (int)newCoord.getY())).getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX() + 1, (int)newCoord.getY()))) {
            room.getConnectedRooms().put(Direction.NORTH, roomMap.get(new Point((int)newCoord.getX() + 1, (int)newCoord.getY())).getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX(), (int)newCoord.getY() - 1))) {
            room.getConnectedRooms().put(Direction.WEST, roomMap.get(new Point((int)newCoord.getX(), (int)newCoord.getY() - 1)).getRoomName());
        }
        if (roomMap.containsKey(new Point((int)newCoord.getX(), (int)newCoord.getY() + 1))) {
            room.getConnectedRooms().put(Direction.EAST, roomMap.get(new Point((int)newCoord.getX(), (int)newCoord.getY() + 1)).getRoomName());
        }
    }

    public void printHouse() {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        for (Map.Entry<Point, Room> entry : roomMap.entrySet()) {
            if (entry.getKey().getX() < minX) {
                minX = (int)entry.getKey().getX();
            }
            if (entry.getKey().getX() > maxX) {
                maxX = (int)entry.getKey().getX();
            }
            if (entry.getKey().getY() < minY) {
                minY = (int)entry.getKey().getY();
            }
            if (entry.getKey().getY() > maxY) {
                maxY = (int)entry.getKey().getY();
            }
        }

        for (int i = minX; i <= maxX; i++) {
            System.out.printf(String.format("%s", "-----------------------"));
            for (int k = maxY-minY; k > 0; k--) {
                System.out.printf(String.format("%s", "----------------------"));
            }
            System.out.println();
            for (int j = minY; j <= maxY; j++) {
                if (roomMap.containsKey(new Point(i, j))) {
                    System.out.printf(String.format("|%-21s", roomMap.get(new Point(i, j)).getRoomName()));
                } else {
                    System.out.printf(String.format("|%-21s", ""));
                }
            }
            System.out.println("|");
        }
        System.out.printf(String.format("%s", "-----------------------"));
        for (int i = maxY-minY; i > 0; i--) {
            System.out.printf(String.format("%s", "----------------------"));
        }
        System.out.println();
    }
}
