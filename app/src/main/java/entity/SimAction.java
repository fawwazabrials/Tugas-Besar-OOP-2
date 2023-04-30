package entity;

import item.Food;
import item.Item;
import map.Room;

public interface SimAction {
    /********************************* PRIMARY ACTIVITY *********************************/
    public void work(int time);
    /*
     * +X money (X sesuai pekerjaan); -10 kekenyangan, -10 mood / 30 detik
     */

    public void workout(int time);
    /*
     * +5 kesehatan, -5 kekenyangan, +10 mood / 20 detik
     */

    public void sleep(int time);
    /*
     * +30 mood, +20 kesehatan / 4 menit
     */

    public void eat(int time, Food food);
    /*
     * +X kekenyangan (X sesuai makanan) / siklus makan(30 detik); Makanan yang dimakan akan hilang dari inventory
     */

    public void cook(int time, Food dish);
    /*
     * +10 mood / makanan yang dimasak; Bahan yang dipakai akan hilang dan Dish akan ditambahkan dari inventory
     * Jika bahan tidak ada/ kurang di invemntory maka method akan mengembalikan error
     */

    public void visit(int time, Sim target);
    /*
     * +10 mood, -10 kekenyangan / 30 detik
     */ 

    public void poop(int time);
    /*
     * -20 kekenyangan, +10 mood / 1 siklus (10 detik)
     */
    


    /********************************* UTIL *********************************/
    public void buyItem(Item item);
    /* 
     * Membeli Item dari toko, jika Sim tidak memiliki cukup uang maka method akan mengembalikan error 
     */

    public void sellItem(Item item);
    /* 
     * Menjual Item dari inventory, jika Sim tidak memiliki barangnya maka method akan mengembalikan error 
     */

    public void move(Room target);
    /*
     * Sim berpindah ruangan
     */
    
     
    /********************************* EXTRA ACTIVITIY *********************************/
    public void stargaze(int time);
    /*
     * +20 mood, -15 kekenyangan / 1 menit
     */

    public void read(int time);
    /*
     * +20 mood, -15 kekenyangan / 1 menit
     */
}
