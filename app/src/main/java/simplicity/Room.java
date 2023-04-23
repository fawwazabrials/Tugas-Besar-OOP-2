package simplicity;
import java.util.*;

public class Room implements RoomAction {
    // Attributes
    private String roomName;
    private final int width;
    private final int length;
    private Map<Direction, Room> connectedRooms;

    private List<Furniture> furnitures;
    private Furniture[][] roomGrid;

    // Constructor
    public Room(String roomName) {
        width = 6;
        length = 6;     
        furnitures = new ArrayList<Furniture>();
        roomGrid = new Furniture[width][length];
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
    public Map<Direction, Room> getConnectedRooms() {return connectedRooms;}
    public List<Furniture> getFurnitures() {return furnitures;}


    // Setter Method
    public void setRoomName(String roomName) {this.roomName = roomName;}
    
    public boolean isPlaceable(Furniture furniture, int x, int y) {
        if (furniture.getWidth() + x > width || furniture.getLength() + y > length ) {
            return false;
        }
        for (int i = x; i < furniture.getWidth() + x; i++) {
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
        for (int i = x; i < width; i++) {
            for (int j = y; j < length; j++) {
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
        
        removeFurniture(oldX, oldY);
        
        if (!isPlaceable(furniture, newX, newY)) {
            throw new IllegalArgumentException("Cannot move furniture to the new location");
        }
    
        addFurniture(furniture, newX, newY);
    }

    public void addFurniture(Furniture furniture, int x, int y) {
        if (! isPlaceable(furniture, x, y)) {
            throw new IllegalArgumentException("Furniture cannot be placed");
        }
        furnitures.add(furniture);
        for (int i = x; i < x + furniture.getWidth(); i++) {
            for (int j = y; j < y + furniture.getLength(); j++) {
                roomGrid[i][j] = furniture;
            }
        }
    }

    // public void printRoom() {
    //     for (int i = 0; i < width; i++) {
    //         for (int j = 0; j < length; j++) {
    //             if (roomGrid[i][j] == null) {
    //                 System.out.print(" ");
    //             } else {
    //                 System.out.print(roomGrid[i][j].getName().charAt(0));
    //             }
    //         }
    //         System.out.println();
    //     }
    // }
}
