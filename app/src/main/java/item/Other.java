package item;

import java.util.HashMap;
import java.util.Map;

public class Other extends Item {
    private static Map<String, String[]> availableOther;
    
    static {
        // key : Nama barang
        // value : [Harga barang, action]
        availableOther = new HashMap<String, String[]>();
        availableOther.put("HP", new String[] {"50", "Gamble"});
        availableOther.put("Sheet QnA", new String[] {"1", "Read QnA"});
    }

    public static Map<String, String[]> getAvailableOther() {return availableOther;}

    private String name;
    private String category;
    private String action;
    private int priceValue;

    public String getName() {return name;}
    public String getCategory() {return category;}
    public String getAction() {return action;}
    public int getPriceValue() {return priceValue;}

    public Other(String name) {
        this.name = name;
        this.category = "other";
        this.priceValue = Integer.parseInt(availableOther.get(name)[0]);
        this.action = availableOther.get(name)[1];
    }
    
}
