package org.example;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Method 1: Extend Thread ===");
        WorldThread world1 = new WorldThread();
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
        lifecycleThread.join();
        System.out.println("State after completion: " + lifecycleThread.getState()); // TERMINATED
    }
}

// Method 1: Extend Thread
class WorldThread extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("World (Thread)");
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
