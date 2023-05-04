package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import util.Angka;

public class Job {
    private Integer pay;
    private String name;

    private static Map<String, Integer> availableJobs;
    static {
        // Default jobs
        availableJobs = new HashMap<String, Integer>();
        availableJobs.put("Badut sulap", 15);
        availableJobs.put("Koki", 30);
        availableJobs.put("Polisi", 35);
        availableJobs.put("Programmer", 45);
        availableJobs.put("Dokter", 50);
    }

    public int getPay() {return pay;}
    public String getName() {return name;}
    public static Map<String, Integer> getAvailableJobs() {return availableJobs;}

    public static List<String> getAvailableJobsList() {
        ArrayList<String> jobList = new ArrayList<String>(); 

        for (String job : getAvailableJobs().keySet()) {
            jobList.add(job);
        }

        return jobList;
    }

    public static int getNumOfAvailableJobs() {
        return availableJobs.size();
    }

    public Job(String name) throws NoSuchElementException {
        this.name = name;
        this.pay = availableJobs.get(name);

        if (pay == null) throw new NoSuchElementException("Tidak ada pekerjaan dengan nama tersebut!");
    }

    public static Job createRandomJob() {
        return new Job(Job.getAvailableJobsList().get(Angka.randint(0, Job.getNumOfAvailableJobs()-1)));
    }

    // Driver
    public static void main(String[] args) {
        Job job = Job.createRandomJob();
        System.out.println(job.getName());
        System.out.println(job.getPay());

        // System.out.println(Job.getAvailableJobsList());
    }
}
