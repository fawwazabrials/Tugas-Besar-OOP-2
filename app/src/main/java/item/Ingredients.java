package item;

public class Ingredients extends

    private String name;
    private String category;
    private int priceValue;
    private int hungerPoint;

    // Constructor
    public Ingredients(String name) {
        this.name = name;
        this.category = "ingredients";
        this.setPriceValue(name);
        this.setHungerPoint(name);
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

    public void setPriceValue(String name) {
        if (name.equals("nasi")) {
            this.priceValue = 5;
        } else if (name.equals("kentang")) {
            this.priceValue = 3;
        } else if (name.equals("ayam")) {
            this.priceValue = 10;
        } else if (name.equals("sapi")) {
            this.priceValue = 12;
        } else if (name.equals("wortel")) {
            this.priceValue = 3;
        } else if (name.equals("kacang")) {
            this.priceValue = 2;
        } else if (name.equals("susu")) {
            this.priceValue = 2;
        }
    }

    public void setHungerPoint(String name) {
        if (name.equals("nasi")) {
            this.hungerPoint = 5;
        } else if (name.equals("kentang")) {
            this.hungerPoint = 4;
        } else if (name.equals("ayam")) {
            this.hungerPoint = 8;
        } else if (name.equals("sapi")) {
            this.hungerPoint = 15;
        } else if (name.equals("wortel")) {
            this.hungerPoint = 2;
        } else if (name.equals("kacang")) {
            this.hungerPoint = 2;
        } else if (name.equals("susu")) {
            this.hungerPoint = 1;
        }
    }
}
