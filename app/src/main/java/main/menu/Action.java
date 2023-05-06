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
        allAvailableAction.put("Cook", new Cook());
        allAvailableAction.put("Eat", new Eat());
        allAvailableAction.put("Poop", new Poop());
        allAvailableAction.put("Visit", new Visit());
        allAvailableAction.put("Work", new Work());
        allAvailableAction.put("Workout", new Workout());
        allAvailableAction.put("See Time", new SeeTime());
        allAvailableAction.put("Stargaze", new Stargaze());
        allAvailableAction.put("Play Game", new PlayGame());
        allAvailableAction.put("Watch TV", new WatchTV());
        allAvailableAction.put("Read", new Read());
        allAvailableAction.put("Gamble", new Gamble());
        allAvailableAction.put("Read QnA", new ReadQNA());
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

        // cuma dari jam 8 malem (menit ke 10 hari) - jam 2 pagi (menit ke 1 hari)
        if (720-gm.getClock().getDayTimeInSec() <= 120 || 720-gm.getClock().getDayTimeInSec() >= 660) availableAction.add("Stargaze");
        System.out.println(720-gm.getClock().getDayTimeInSec());
        System.out.println(720-gm.getClock().getDayTimeInSec() <= 60);
        System.out.println(720-gm.getClock().getDayTimeInSec() >= 600);


        if (gm.getCurrentSim().getSimItems().checkItemAvailable("HP", 1)) availableAction.add("Gamble");

        if (gm.getCurrentSim().getSimItems().checkItemAvailable("Sheet QnA", 1)) availableAction.add("Read QnA");

        // System.out.println(gm.getCurrentSim().getSimItems().checkItemAvailable("HP", 1));

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
