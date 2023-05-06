package item;

@SuppressWarnings("unused")
public abstract class Item {
    protected String name;
    protected String category;
    protected int priceValue;

    public Item(String name, String category, int priceValue) {
        this.name = name;
        this.category = category;
        this.priceValue = priceValue;
    }

    public abstract String getName();
    public abstract String getCategory();
    public abstract int getPriceValue();

}