package map;

public interface HouseAction {
    public void addRoom(String roomName, Room benchmarkRoom, Direction direction);
    /*
     * Menambahkan Room dengan nama roomName di House. Ruangan akan ditempelkan pada direction di Room target. 
     * Jika Room tidak bisa ditambahkan, method akan mengembalikan error
     */

     public void removeRoom(Room room);
    /*
     * PREREQ: Room sudah pasti ada
     * Menghapus Room dari house.
     */

    public void moveRoom(Room room, Room benchmarkRoom, Direction direction);
    /*
     * Memindahkan oldRoom ke tempat baru.Ruangan akan ditempelkan pada direction di Room target. 
     * Jika Room tidak bisa ditambahkan, method akan mengembalikan error
     */
}
