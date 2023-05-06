package item;

public class Ingredients extends Food {
    // Constructor
    public Ingredients(String name) {
        super(name, "ingredients", setPriceValue(name), setHungerPoint(name));
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public int getHungerPoint() {
        return hungerPoint;
    }

    public static int setPriceValue(String name) {
        if (name.equals("nasi")) {
            return 5;
        } else if (name.equals("kentang")) {
            return 3;
        } else if (name.equals("ayam")) {
            return 10;
        } else if (name.equals("sapi")) {
            return 12;
        } else if (name.equals("wortel")) {
            return 3;
        } else if (name.equals("kacang")) {
            return 2;
        } else if (name.equals("susu")) {
            return 2;
        } return 0;
    }

    public static int setHungerPoint(String name) {
        if (name.equals("nasi")) {
            return 5;
        } else if (name.equals("kentang")) {
            return 4;
        } else if (name.equals("ayam")) {
            return 8;
        } else if (name.equals("sapi")) {
            return 15;
        } else if (name.equals("wortel")) {
            return 2;
        } else if (name.equals("kacang")) {
            return 2;
        } else if (name.equals("susu")) {
            return 1;
        } return 0;
    }
}
