package simplicity;

public interface HouseAction {
    public void addRoom(String roomName, Room benchmarkRoom, Direction direction);
    /*
     * Menambahkan Room baru pada House. Room baru akan bersebelahan dengan benchmarkRoom pada 
     * arah direction. Jika benchmarkRoom tidak ada atau sudah bersebelahan dengan Room lain pada 
     * arah direction, method akan mengembalikan error
     */

     public void removeRoom(Room room);
    /*
    * Menghapus Room dari House. Jika Room tidak ada, method akan mengembalikan error
    */

    public void moveRoom(Room room, Room benchmarkRoom, Direction direction);
    /*
     * Memindahkan Room dari House. Room akan bersebelahan dengan benchmarkRoom pada arah direction. 
     * Jika benchmarkRoom tidak ada atau sudah bersebelahan dengan Room lain pada arah direction, 
     * method akan mengembalikan error
     */
}
