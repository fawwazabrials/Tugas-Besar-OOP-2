package item;

@SuppressWarnings("unused")
public abstract class Food extends Item {
    protected int hungerPoint;

    public Food(String name, String category, int priceValue, int hungerPoint) {
        super(name, category, priceValue);
        this.hungerPoint = hungerPoint;
    }

    public abstract int getHungerPoint();
}