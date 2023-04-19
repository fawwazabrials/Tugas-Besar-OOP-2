package simplicity;

public interface WorldAction {
    public void addHouse(int x, int y);
    /* 
     * Tambah rumah dari World pada koordinat (x, y). Jika sudah ada rumah, method akan mengembalikan error
     */

    public void removeHouse(int x, int y);
    /*
     * Hapus rumah dari World pada koordinat (x, y). Jika tidak ada rumah, method akan mengembalikan error
     */
}
