package org.example;

public class ThreadDemo {

    //main thread starts as soon as java application is executed
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Method 1: Extend Thread ===");
        WorldThread world1 = new WorldThread("World1");
        world1.start();
        Thread.sleep(100);
        world1.interrupt(); // Stop it from printing infinitely

        System.out.println("\n=== Method 2: Implement Runnable ===");
        WorldRunnable world2 = new WorldRunnable();
        Thread thread2 = new Thread(world2);
        thread2.start();
        Thread.sleep(100); // Let it print a few lines
        thread2.interrupt();

        System.out.println("\n=== Thread Lifecycle Demo ===");
        MyThread lifecycleThread = new MyThread();
        System.out.println("State after creation: " + lifecycleThread.getState()); // NEW
        lifecycleThread.start();
        System.out.println("State after start(): " + lifecycleThread.getState()); // RUNNABLE
        Thread.sleep(100); // Give time to enter sleep
        System.out.println("State during sleep: " + lifecycleThread.getState()); // TIMED_WAITING
        lifecycleThread.join();  //wait for lifecycleThread to finish then go to next thread
        System.out.println("State after completion: " + lifecycleThread.getState()); // TERMINATED

        //Set priority
        System.out.println("Thread Priority examples: ");

        MyPriorityThread L = new MyPriorityThread("High Priority");
        MyPriorityThread M = new MyPriorityThread("Med Priority");
        MyPriorityThread H = new MyPriorityThread("Low Priority");

        L.setPriority(Thread.MIN_PRIORITY);
        M.setPriority(Thread.NORM_PRIORITY);
        H.setPriority(Thread.MAX_PRIORITY);

        L.start();
        M.start();
        H.start();

        //Interrupt Operation
        MyInterruptThread t1 = new MyInterruptThread();
        t1.start();
        t1.interrupt();

        //yield method -> gives hint to
        WorldThread t2 = new WorldThread("yield t2");
        WorldThread t3 = new WorldThread("yield t3");
        t2.start();
        t3.start();

        // User Threads -> Threads we create to do work
        // Demon Threads -> Runs in Background -> JVM don't wait for these threads
        WorldThread t4 = new WorldThread("Demon 43");
        t4.setDaemon(true);
        t4.start();

        System.out.println("main done");


    }
}
class MyPriorityThread extends Thread {
    public MyPriorityThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0 ;  i < 5 ; i++ ){
            StringBuilder s = new StringBuilder();
            for (int j = 0 ; j < 1000000 ; j++) s.append('a');
            System.out.println(Thread.currentThread().getName() + " - Priority : " + Thread.currentThread().getPriority());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
// Method 1: Extend Thread
class WorldThread extends Thread {
    public WorldThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0 ; i< 5 ; i++){
            System.out.println(Thread.currentThread().getName() );
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Thread.yield();
        }
    }
}

// Method 2: Implement Runnable
class WorldRunnable implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("World (Runnable)");
        }
    }
}

// Thread Lifecycle Demo
class MyInterruptThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e);
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is RUNNING...");
        try {
            Thread.sleep(2000); // Simulate work
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e);
        }
    }
}
