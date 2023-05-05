package item;

import java.util.HashMap;
import java.util.Map;

public class Furniture extends Item {

    private static Map<String, String[]> availableFurniture;
    static {
        // key : Nama barang
        // value : [Action, price, length, width, renderchar]
        availableFurniture = new HashMap<String, String[]>();
        availableFurniture.put("kasur single", new String[]     {"Sleep", "50", "4", "1", "b"});
        availableFurniture.put("kasur queen size", new String[] {"Sleep", "100", "4", "1", "b"});
        availableFurniture.put("kasur king size", new String[]  {"Sleep", "150", "4", "1", "b"});
        availableFurniture.put("toilet", new String[]           {"Poop", "50", "4", "1", "b"});
        availableFurniture.put("kompor gas", new String[]       {"Cook", "100", "4", "1", "b"});
        availableFurniture.put("kompor listrik", new String[]   {"Cook", "200", "4", "1", "b"});
        availableFurniture.put("meja dan kursi", new String[]   {"Eat", "50", "4", "1", "b"});
        availableFurniture.put("jam", new String[]              {"See Time", "10", "4", "1", "b"});
    }

    public static Map<String, String[]> getAvailableFurniture() {return availableFurniture;}

    // Attributes
    private int x, y;
    private String name;
    private String category;
    private String renderChar;
    private String action;
    private int priceValue;
    private int width;
    private int length;

    // Constructor
    public Furniture(String name, int x, int y) {
        this.x = x;
        this.y = y;

        this.name = name;
        this.category = "furniture";
        this.setPrice(name);
        this.setLength(name);
        this.setWidth(name);
        this.setRenderChar(name);
        this.setAction(name);
    }
    
    public int getX() {return x;}
    public void setX(int newX) {x = newX;}
    public int getY() {return y;}
    public void setY(int newY) {y = newY;}
    public String getAction() {return action;}
    public String getName() {return name;}
    public String getCategory() {return category;}
    public int getPriceValue() {return priceValue;}
    public int getWidth() {return width;}
    public int getLength() {return length;}
    public char getRenderChar() {return renderChar.charAt(0);}

    private void setAction(String name) {
        this.action = getAvailableFurniture().get(name)[0];
    }

    private void setPrice(String name) {
        this.action = getAvailableFurniture().get(name)[1];
    }

    private void setLength(String name) {
        this.action = getAvailableFurniture().get(name)[2];
    }

    private void setWidth(String name) {
        this.action = getAvailableFurniture().get(name)[3];
    }

    private void setRenderChar(String name) {
        this.action = getAvailableFurniture().get(name)[4];
    }
}