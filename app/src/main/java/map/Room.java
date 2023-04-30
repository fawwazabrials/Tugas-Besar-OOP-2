package map;
import java.util.*;

import item.Furniture;

public class Room implements RoomAction, Renderable {
    // Attributes
    private String roomName;
    private final int width = 6;
    private final int length = 6;
    private Map<Direction, Room> connectedRooms;

    private List<Furniture> furnitures;
    private Furniture[][] roomGrid;

    // Constructor
    public Room(String roomName) {   
        furnitures = new ArrayList<Furniture>();
        roomGrid = new Furniture[width][length];
        createBlankRoom(); // Isi grid sama null semua

        connectedRooms = new EnumMap<>(Direction.class);

        this.roomName = roomName;
           
        connectedRooms.put(Direction.NORTH, null);
        connectedRooms.put(Direction.SOUTH, null);
        connectedRooms.put(Direction.EAST, null);
        connectedRooms.put(Direction.WEST, null);
    }

    public void createBlankRoom() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                roomGrid[i][j] = null;
            }
        }
    }

    // Getter Method
    public String getRoomName() {return roomName;}
    public int getWidth() {return width;}
    public int getLength() {return length;}
    public Map<Direction, Room> getConnectedRooms() {return connectedRooms;}
    public List<Furniture> getFurnitures() {return furnitures;}


    // Setter Method
    public void setRoomName(String roomName) {this.roomName = roomName;}
    
    public boolean isPlaceable(Furniture furniture, int x, int y) {
        if (furniture.getLength() + x > width || furniture.getLength() + y > length ) {
            return false;
        }
        for (int i = x; i < furniture.getLength() + x; i++) {
            for (int j = y; j < furniture.getLength() + y; j++) {
                if (roomGrid[x][y] != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void removeFurniture(int x, int y) {
        Furniture furniture = roomGrid[x][y];
        if (furniture == null) {
            throw new IllegalArgumentException("No furniture at (" + x + ", " + y + ")");
        }
        furnitures.remove(furniture);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (roomGrid[i][j] == furniture) {
                    roomGrid[i][j] = null;
                }
            }
        }
    }

     public void moveFurniture(int oldX, int oldY, int newX, int newY) {
        Furniture furniture = roomGrid[oldX][oldY];
        if (furniture == null) {
            throw new IllegalArgumentException("No furniture at (" + oldX + ", " + oldY + ")");
        }

        // Mencari titik paling atas kiri dari furniture
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
            throw new IllegalArgumentException("No furniture at (" + oldX + ", " + oldY + ")");
        }

        int x = newX - (oldX - topLeftX);
        int y = newY - (oldY - topLeftY);

        if (!isPlaceable(furniture, x, y)) {
            
            roomGrid = tempRoomGrid;
            furnitures.add(furniture);
            throw new IllegalArgumentException("Cannot move furniture to the new location");
        }
        addFurniture(furniture, x, y);
    }

    public void addFurniture(Furniture furniture, int x, int y) {
        if (! isPlaceable(furniture, x, y)) {
            throw new IllegalArgumentException("Furniture cannot be placed");
        }
        furnitures.add(furniture);
        for (int i = x; i < x + furniture.getLength(); i++) {
            for (int j = y; j < y + furniture.getLength(); j++) {
                roomGrid[i][j] = furniture;
            }
        }
    }

    public int[][] render() {
        int[][] renderMap = new int[width][length];

        for (int i=0; i<width; i++) {
            for (int j=0; j<length; j++) {
                if (roomGrid[i][j] == null) {
                    renderMap[i][j] = 0;
                }
            }
        }

        return renderMap;
    }
}
