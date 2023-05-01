package item;

public abstract class Food extends Item {
    private String name;
    private String category;
    private int hungerPoint;
    private int priceValue;

    public abstract String getName();
    
    public abstract String getCategory();

    public abstract int getHungerPoint();

    public abstract int getPriceValue();
}