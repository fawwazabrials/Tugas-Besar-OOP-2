package main;

import javax.swing.*;

public class UIManager {
    private JButton start = new JButton("Start Game");
    private JButton options = new JButton("Options");
    private JButton exit = new JButton("Exit");

    private JButton addsim = new JButton("Add Sim");
    private JButton sim = new JButton("Sim");
    private JButton build = new JButton("Build");
    private JButton inv = new JButton("Inventory");

    private JButton place = new JButton("Place");
    private JButton changesim = new JButton("Change Sim");
    private JButton eat = new JButton("Eat");

    private CircularButton pause = new CircularButton(/*icon pause*/);
    private CircularButton cancel = new CircularButton(/*icon x*/);
    private CircularButton upgrade = new CircularButton(/*icon upgrade*/);
    private CircularButton remove = new CircularButton(/*icon remove*/);
}
