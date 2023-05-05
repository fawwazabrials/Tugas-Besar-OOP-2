package main.menu;

import java.util.HashMap;
import java.util.Map;

import main.Game;
import util.Input;

public class Menu {
    private Game gm;
    private Option option;
    private Input scan = Input.getInstance();

    private Map<String, Option> availableMenuOptions;

    public Menu(Game gm) {
        this.gm = gm;

        availableMenuOptions = new HashMap<String, Option>();
        availableMenuOptions.put("S", new SimInfo());
        availableMenuOptions.put("C", new ChangeJob());
        // availableMenuOptions.put("I", new Inventory());  // TODO: Route this
        // availableMenuOptions.put("U", new UpgradeHouse());  // TODO: Route this
        // availableMenuOptions.put("M", new MoveSim());  // TODO: Route this
        // availableMenuOptions.put("E", new EditRoom());  // TODO: Route this
        availableMenuOptions.put("Ad", new AddSim());
        availableMenuOptions.put("Ch", new ChangeSim());
        availableMenuOptions.put("L", new ListObject());
        availableMenuOptions.put("G", new GoToObject());
        availableMenuOptions.put("A", new ActionMenu(new Action(gm)));  // TODO: Route this
        // availableMenuOptions.put("S", new Shop());  // TODO: Route this
        availableMenuOptions.put("E", new Exit());
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public void executeOption() {
        option.execute(gm);
    }

    public void executeOption(Option option) {
        setOption(option);
        option.execute(gm);
    }

    public void showMenuOptions() {
        System.out.println("\n(S)im Info        (C)hange Job  (I)nventory");
        System.out.println("(U)pgrade House   (M)ove Sim          (E)dit Room");
        System.out.println("(Ad)dd Sim        (Ch)ange Sim        (L)ist Object  ");
        System.out.println("(G)o to Object    (A)ction            (S)hop");
        System.out.println("                 E(X)it");
    }

    public void getMenuInput() {
        
    }
}
