package main.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import main.Game;

public class Action {
    private Map<String, Option> allAvailableAction;
    private Game gm;

    public Action(Game gm) {
        this.gm = gm;

        allAvailableAction = new LinkedHashMap<String, Option>();
        allAvailableAction.put("Sleep", new Sleep());
        // allAvailableAction.put("Cook", new Sleep()); // TODO: route this
        // allAvailableAction.put("Eat", new Sleep()); // TODO: route this
        allAvailableAction.put("Poop", new Poop());
        allAvailableAction.put("Visit", new Visit());
        allAvailableAction.put("Work", new Work());
        allAvailableAction.put("Workout", new Workout());
        allAvailableAction.put("See Time", new SeeTime());
        // allAvailableAction.put("Stargaze", new Sleep()); // TODO: implement this
        allAvailableAction.put("Play Game", new PlayGame()); // TODO: implement this
        // allAvailableAction.put("Watch TV", new SeeTime()); // TODO: implement this
        // allAvailableAction.put("Read", new SeeTime()); // TODO: implement this
        // allAvailableAction.put("Gamble", new SeeTime()); // TODO: implement this
        // allAvailableAction.put("Read QnA", new SeeTime()); // TODO: implement this
    }

    public Option getOverlapAction() {
        return allAvailableAction.get(gm.getOverlapFurniture().getAction());
    }

    public List<String> getListOfAvailableAction() {
        List<String> availableAction = new ArrayList<String>();

        availableAction.add("Work");
        availableAction.add("Workout");
        availableAction.add("Visit");

        if (gm.getOverlapFurniture() != null) availableAction.add(gm.getOverlapFurniture().getAction());

        // TODO: Tambah conditional stargaze
        // TODO: Tambah conditional QnA

        return availableAction;
    }

    public void execute(String action) {
        Option aksi = allAvailableAction.get(action);
        if (aksi == null) throw new IllegalArgumentException("Tidak ada aksi dengan nama tersebut!");
        aksi.execute(gm);
    }

    public void execute(Option action) {
        action.execute(gm);
    }

}
