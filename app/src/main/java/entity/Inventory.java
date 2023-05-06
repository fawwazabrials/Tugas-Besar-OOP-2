package entity;

import java.util.*;

import item.*;

public class Inventory {
    private Map<Item, Integer> items;

    public Inventory(){
        this.items = new HashMap<Item, Integer>();
    }

    public synchronized Map<Item, Integer> getItems(String category){
        Map<Item, Integer> wanted = new HashMap<>();

        if (category == "food") {
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                if(e.getKey().getCategory().equals("ingredients") || e.getKey().getCategory().equals("dish")){
                    wanted.put(e.getKey(), e.getValue());
                }
            }
        } else {
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                if(e.getKey().getCategory().equals(category)){
                    wanted.put(e.getKey(), e.getValue());
                }
            }
        }
        return wanted;
    }

    public synchronized Map<Item, Integer> getItems() {
        return items;
    }

    public synchronized Item getItemsByName(String name){
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            if(e.getKey().getName().equals(name)){
                return e.getKey();
            }
        }
        return null;
    }

    public synchronized void addItem(Item in){
        if(items.containsKey(in)){
            items.put(in, items.get(in) + 1);
        }
        else{
            items.put(in, 1);
        }
    }

    public synchronized void removeItem(Item out){
        if(items.get(out) > 1){
            items.put(out, items.get(out) - 1);
        }
        else{
            items.remove(out);
        }
    }

    public synchronized void removeItem(String out) {
        removeItem(getItemsByName(out));
    }
    
    public synchronized void printInv(){
        if (items.isEmpty()) {
            System.out.println(String.format("| %-51s |" , "No items in inventory."));
        } else {
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                System.out.println(String.format("| %-15s | %-15s | %-15s |", e.getKey().getName(), e.getKey().getCategory(), e.getValue()));
            }
        }
        System.out.println(String.format(" %s ", "-----------------------------------------------------"));
    }

    public synchronized void print(String category) {
        Map<Item, Integer> items = getItems(category);

        System.out.println(String.format(" %s ", "--------------------- INVENTORY ---------------------"));
        System.out.println(String.format("%s", "|                                                     |"));
        
        if (items.isEmpty())
        System.out.println(String.format("| %-51s |" , "No items in inventory."));
        
        else {
            System.out.println(String.format("| %-15s | %-15s | %-15s |", "Item Name", "Category", "Amount"));
            System.out.println(String.format(" %s ", "-----------------------------------------------------"));
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                System.out.println(String.format("| %-15s | %-15s | %-15s |", e.getKey().getName(), e.getKey().getCategory(), e.getValue()));
            }
        }

        System.out.println(String.format(" %s ", "-----------------------------------------------------"));
    }

    public synchronized void print() {
        Map<Item, Integer> items = getItems();

        System.out.println(String.format(" %s ", "--------------------- INVENTORY ---------------------"));
        System.out.println(String.format("%s", "|                                                     |"));
        
        if (items.isEmpty())
        System.out.println(String.format("| %-51s |" , "No items in inventory."));
        
        else {
            System.out.println(String.format("| %-15s | %-15s | %-15s |", "Item Name", "Category", "Amount"));
            System.out.println(String.format(" %s ", "-----------------------------------------------------"));
            for(Map.Entry<Item, Integer> e : items.entrySet()){
                System.out.println(String.format("| %-15s | %-15s | %-15s |", e.getKey().getName(), e.getKey().getCategory(), e.getValue()));
            }
        }

        System.out.println(String.format(" %s ", "-----------------------------------------------------"));
    }

    public synchronized boolean checkItemAvailable(String name, int num){
        for(Map.Entry<Item, Integer> e : items.entrySet()){
            if(e.getKey().getName().equals(name)){
                return num >= e.getValue();
            }
        }
        return false;
    }
}