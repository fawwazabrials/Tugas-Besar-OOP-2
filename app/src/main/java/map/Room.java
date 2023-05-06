package map;
import java.util.*;

import item.Furniture;

public class Room implements Renderable {
    // Attributes
    private String roomName;
    private final int width = 6;
    private final int length = 6;
    private Map<Direction, String> connectedRooms;

    private List<Furniture> furnitures;
    private Furniture[][] roomGrid;

    // Constructor
    public Room(String roomName) {   
        furnitures = new ArrayList<Furniture>();
        roomGrid = new Furniture[width][length];
        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                roomGrid[i][j] = null;
            }
        }

        connectedRooms = new EnumMap<>(Direction.class);

        this.roomName = roomName;
           
        connectedRooms.put(Direction.NORTH, null);
        connectedRooms.put(Direction.SOUTH, null);
        connectedRooms.put(Direction.EAST, null);
        connectedRooms.put(Direction.WEST, null);
    }

    // Getter Method
    public String getRoomName() {return roomName;}
    public int getWidth() {return width;}
    public int getLength() {return length;}
    public Map<Direction, String> getConnectedRooms() {return connectedRooms;}
    public List<Furniture> getFurnitures() {return furnitures;}
    public Furniture[][] getRoomGrid() {return roomGrid;}


    // Setter Method
    public void setRoomName(String roomName) {this.roomName = roomName;}
    
    public static Room makeDefaultRoom() {
        Room room = new Room("Bed Room");
        room.addFurniture(new Furniture("kasur single", 0, 0), 0, 0);
        room.addFurniture(new Furniture("toilet", 5, 5), 5, 5);
        room.addFurniture(new Furniture("kompor gas", 5, 2), 5, 2);
        room.addFurniture(new Furniture("meja dan kursi", 2, 0), 2, 0);
        room.addFurniture(new Furniture("jam", 0, 5), 0, 5);
        return room;
    }

    public boolean isPlaceable(Furniture furniture, int x, int y) {
        if (furniture.getWidth() + x > width || furniture.getLength() + y > length ) {
            return false;
        }
        for (int i = x; i < x + furniture.getWidth(); i++) {
            for (int j = y; j < y + furniture.getLength(); j++) {
                if (roomGrid[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Furniture removeFurniture(int x, int y) {
        Furniture furniture = roomGrid[x][y];
        if (furniture == null) {
            throw new IllegalArgumentException("\nTidak ada furniture di koordinat ini!");
        }
        furnitures.remove(furniture);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (roomGrid[i][j] == furniture) {
                    roomGrid[i][j] = null;
                }
            }
        }

        furniture.setX(-1);
        furniture.setY(-1);

        return furniture;
    }

     public void moveFurniture(int oldX, int oldY, int newX, int newY) {
        Furniture furniture = roomGrid[oldX][oldY];
        if (furniture == null) {
            throw new IllegalArgumentException("\nTidak ada furniture di koordinat ini!");
        }

        int topLeftX = -1;
        int topLeftY = -1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (roomGrid[i][j] == furniture) {
                    topLeftX = i;
                    topLeftY = j;
                    break;
                }
            }
            if (topLeftX != -1 && topLeftY != -1) {
                break;
            }
        }

        // Clone roomGrid
        Furniture[][] tempRoomGrid = new Furniture[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                tempRoomGrid[i][j] = roomGrid[i][j];
            }
        }

        removeFurniture(oldX, oldY);

        // Mengatasi jika topLeft tidak diinisalisasi akan throw error
        if (topLeftX == -1 || topLeftY == -1) {
            throw new IllegalArgumentException("\nTidak ada furniture di koordinat ini!");
        }

        int x = newX - (oldX - topLeftX);
        int y = newY - (oldY - topLeftY);

        if (!isPlaceable(furniture, x, y)) {
            
            roomGrid = tempRoomGrid;
            furnitures.add(furniture);
            throw new IllegalArgumentException("\nFurniture tidak bisa dipindahkan ke lokasi baru!");
        }
        addFurniture(furniture, x, y);
    }
    
    public void addFurniture(Furniture furniture, int x, int y) {
        if (! isPlaceable(furniture, x, y)) {
            throw new IllegalArgumentException("\nFurniture tidak bisa ditambahkan!");
        }
        furniture.setX(x);
        furniture.setY(y);

        furnitures.add(furniture);
        for (int i = x; i < x + furniture.getWidth(); i++) {
            for (int j = y; j < y + furniture.getLength(); j++) {
                roomGrid[i][j] = furniture;
            }
        }
    }

    public char[][] render() {
        char[][] renderMap = new char[width][length];

        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                if (roomGrid[i][j] == null) {
                    renderMap[i][j] = '.';
                } else {
                    renderMap[i][j] = roomGrid[i][j].getRenderChar();
                }
            }
        }
        return renderMap;
    }
}
