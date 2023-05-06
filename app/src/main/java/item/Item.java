package item;

@SuppressWarnings("unused")
public abstract class Item {
    protected String name;
    protected String category;
    protected int priceValue;

    public abstract String getName();

    public abstract String getCategory();

    public abstract int getPriceValue();

}