package item;

public class Dish extends Food {

    // Constructor
    public Dish(String name) {
        this.name = name;
        this.category = "dish";
        this.setPriceValue(name);
        this.setHungerPoint(name);
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

    public void setPriceValue(String name) {
        /* price value dari dish merupakan penjumlahan seluruh price value 
         * dari setiap ingredientsnya
         */
        if (name.equals("nasi ayam")) {
            this.priceValue = 15;
        } else if (name.equals("nasi kari")) {
            this.priceValue = 28;
        } else if (name.equals("susu kacang")) {
            this.priceValue = 4;
        } else if (name.equals("tumis sayur")) {
            this.priceValue = 6;
        } else if (name.equals("bistik")) {
            this.priceValue = 15;
        }
    }

    public void setHungerPoint(String name) {
        if (name.equals("nasi ayam")) {
            this.hungerPoint = 16;
        } else if (name.equals("nasi kari")) {
            this.hungerPoint = 30;
        } else if (name.equals("susu kacang")) {
            this.hungerPoint = 5;
        } else if (name.equals("tumis sayur")) {
            this.hungerPoint = 5;
        } else if (name.equals("bistik")) {
            this.hungerPoint = 22;
        }
    }
}
