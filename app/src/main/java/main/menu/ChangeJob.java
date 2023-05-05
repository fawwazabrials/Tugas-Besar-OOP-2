package main.menu;

import java.util.NoSuchElementException;

import entity.Job;
import exception.NoInputException;
import main.Game;
import util.Input;

public class ChangeJob implements Option {

    Input scan = Input.getInstance();

    @Override
    public void execute(Game gm) {
        System.out.println("\n    Pekerjaan yang ada: ");
        System.out.println(String.format(" %s ", "--------------------------"));
        System.out.println(String.format("| %-15s | %-6s |", "Pekerjaan", "Gaji"));
        System.out.println(String.format("|%s|", "--------------------------"));
        for (String s : Job.getAvailableJobsList()) {
            System.out.println(String.format("| %-15s | %-6s |", s, Job.getAvailableJobs().get(s)));
        }
        System.out.println(String.format(" %s ", "--------------------------"));

        System.out.println("\nSim hanya bisa mengganti pekerjaan jika sudah bekerja selama minimal 1 hari di pekerjaan lamanya");
        System.out.println("Sim juga harus membayarkan setengah gaji dari pekerjaan baru untuk mengganti pekerjaan");

        try {
            String input = scan.getInput("\nMasukkan pekerjaan yang dipilih: ");

            try {
                gm.getCurrentSim().changeJob(new Job(input));
                System.out.println("\nPekerjaan sim berhasil diganti!");
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            scan.enterUntukLanjut();

        } catch (NoInputException e) {
            // ingoner
        }   
    }

}
