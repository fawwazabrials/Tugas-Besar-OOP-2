package item;

public class Furniture extends Item {
    // Attributes
    private String name;
    private String category;
    private int priceValue;
    private int width;
    private int length;

    // Constructor
    public Furniture(String name, String category, int priceValue, int width, int length) {
        this.name = name;
        this.category = category;
        this.priceValue = priceValue;
        this.width = width;
        this.length = length;
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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}