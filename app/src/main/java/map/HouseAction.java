package map;

public interface HouseAction {
    public void addRoom(Room target, String direction, String roomName);
    /*
     * Menambahkan Room dengan nama roomName di House. Ruangan akan ditempelkan pada direction di Room target. 
     * Jika Room tidak bisa ditambahkan, method akan mengembalikan error
     */

    public void removeRoom(Room target);
    /*
     * PREREQ: Room sudah pasti ada
     * Menghapus Room dari house.
     */

    public void moveRoom(Room oldRoom, Room target, String direction);
    /*
     * Memindahkan oldRoom ke tempat baru.Ruangan akan ditempelkan pada direction di Room target. 
     * Jika Room tidak bisa ditambahkan, method akan mengembalikan error
     */
}
