package item;

@SuppressWarnings("unused")
public abstract class Item {
    private String name;
    private String category;
    private int priceValue;

    public abstract String getName();

    public abstract String getCategory();

    public abstract int getPriceValue();

}