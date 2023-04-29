package map;

public interface RoomAction {
    public void addFurniture(Furniture furniture, int x, int y);
    /* 
     * Menaruh Furniture di Room pada koordinat (x, y). Jika tidak bisa ditaruh, method akan mengembalikan error
     */

    public void removeFurniture(int x, int y);
    /*
     * Menghapus Furniture di Room pada koordinat (x, y). Jika tidak ada Furniture, method akan mengembalikan error
     */

    public void moveFurniture(int oldX, int oldY, int newX, int newY);
    /*
     * Memindahkan Furniture di Room pada koordinat (oldX, oldY) ke koordinat (newX, newY). Jika 
     * Furniture tidak ada atau tidak bisa ditaruh, method akan mengembalikan error
     */
}
