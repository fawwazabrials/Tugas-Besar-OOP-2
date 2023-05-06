package item;

public class Dish extends Food {

    // Constructor
    public Dish(String name) {
        super(name, "dish", setPriceValue(name), setHungerPoint(name));
        this.name = name;
        this.category = "dish";
    }

    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }

    public int getHungerPoint() {
        return hungerPoint;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public static int setPriceValue(String name) {
        /* price value dari dish merupakan penjumlahan seluruh price value 
         * dari setiap ingredientsnya
         */
        if (name.equals("nasi ayam")) {
            return 15;
        } else if (name.equals("nasi kari")) {
            return 28;
        } else if (name.equals("susu kacang")) {
            return 4;
        } else if (name.equals("tumis sayur")) {
            return 6;
        } else if (name.equals("bistik")) {
            return 15;
        } return 0;
    }

    public static int setHungerPoint(String name) {
        if (name.equals("nasi ayam")) {
            return 16;
        } else if (name.equals("nasi kari")) {
            return 30;
        } else if (name.equals("susu kacang")) {
            return 5;
        } else if (name.equals("tumis sayur")) {
            return 5;
        } else if (name.equals("bistik")) {
            return 22;
        } return 0;
    }
}
