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
        // Print pekerjaan yang ada
        System.out.println(String.format(" %s ", "--------------------------"));
        System.out.println(String.format("| %-15s | %-6s |", "Pekerjaan", "Gaji"));
        System.out.println(String.format("|%s|", "--------------------------"));
        for (String s : Job.getAvailableJobsList()) {
            System.out.println(String.format("| %-15s | %-6s |", s, Job.getAvailableJobs().get(s)));
        }
        System.out.println(String.format(" %s ", "--------------------------"));

        // Print disclaimer
        System.out.println("----------------------------- DISLAIMER -----------------------------");
        System.out.println(String.format("| %-65s |", "Sim hanya bisa mengganti pekerjaan jika sudah bekerja selama"));
        System.out.println(String.format("| %-65s |", "minimal 1 hari di pekerjaan lamanya. Sim juga harus membayarkan "));
        System.out.println(String.format("| %-65s |", "setengah gaji dari pekerjaan baru untuk mengganti pekerjaan"));
        System.out.println("---------------------------------------------------------------------");

        try {
            String input = scan.getInput("\nMasukkan nama pekerjaan yang dipilih: ");

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
