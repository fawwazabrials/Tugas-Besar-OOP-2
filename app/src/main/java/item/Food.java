package item;

public class Food extends Item {
    private String name;
    private String category;
    private int priceValue;
    private int hungerPoint;

    public Food(String name, String category, int priceValue, int hungerPoint) {
        this.name = name;
        this.category = category;
        this.priceValue = priceValue;
        this.hungerPoint = hungerPoint;
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
}