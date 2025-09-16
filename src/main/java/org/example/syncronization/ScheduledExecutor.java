package org.example.syncronization;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Hello at " + (System.currentTimeMillis() - startTime) / 1000 + "s");

        // Schedule the task to run every 5 seconds, starting after 5 seconds
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(task, 5, 5, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(() -> {
                    System.out.println("Task after 5 sec");
                },
                5,
                5, //wait after prev task is complete
                TimeUnit.SECONDS);

        // Stop the scheduler after 20 seconds
        scheduler.schedule(() -> {
            System.out.println("Stopping scheduler at " + (System.currentTimeMillis() - startTime) / 1000 + "s");
            scheduledFuture.cancel(true);
            scheduler.shutdown();
        }, 20, TimeUnit.SECONDS);
    }
}
