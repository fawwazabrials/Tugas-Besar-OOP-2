package entity;

import java.util.*;

import item.*;

public class Inventory {
    private Map<Item, Integer> items;

    public Inventory(){
        this.items = new HashMap<Item, Integer>();
    }

    public Map<Item, Integer> getItems(String category){
        Map<Item, Integer> wanted = new HashMap<>();
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            if(e.getKey().getCategory().equals(category)){
                wanted.put(e.getKey(), e.getValue());
            }
        }
        return wanted;
    }

    public Item getItemsByName(String name){
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            if(e.getKey().getName().equals(name)){
                return e.getKey();
            }
        }
        return null;
    }

    public void addItem(Item in){
        if(items.containsKey(in)){
            items.put(in, items.get(in) + 1);
        }
        else{
            items.put(in, 1);
        }
    }

    public void removeItem(Item out){
        if(items.get(out) > 1){
            items.put(out, items.get(out) - 1);
        }
        else{
            items.remove(out);
        }
    }
    
    public void printInv(){
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            System.out.println(e.getKey().getName()+" - "+e.getKey().getCategory()+" - "+e.getValue());
        }
    }

    public boolean checkItemAvailable(String name, int num){
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            if(e.getKey().getName().equals(name)){
                return num < e.getValue();
            }
        }
        return false;
    }
}