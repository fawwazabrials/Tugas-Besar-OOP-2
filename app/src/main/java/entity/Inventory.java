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
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                if (e.getKey().getName().length() >= 16) {
                    System.out.println(e.getKey().getName()+"\t"+e.getKey().getCategory()+"\t\t"+e.getValue());
                }
                else if (e.getKey().getName().length() >= 8) {
                    System.out.println(e.getKey().getName()+"\t\t"+e.getKey().getCategory()+"\t\t"+e.getValue());
                }
                else {
                    System.out.println(e.getKey().getName()+"\t\t\t"+e.getKey().getCategory()+"\t\t"+e.getValue());
                }
            }
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