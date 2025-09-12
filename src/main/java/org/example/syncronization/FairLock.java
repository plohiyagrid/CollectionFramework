package org.example.syncronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLock {
    private final Lock unfairLock = new ReentrantLock(true);

    public void accessResource(){
        unfairLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquire the lock.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release the lock.");
            unfairLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FairLock example = new FairLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                example.accessResource();
            }
        };

        Thread t1 = new Thread(task , "Thread-1");
        Thread t2 = new Thread(task , "Thread-2");
        Thread t3 = new Thread(task , "Thread-3");

        t1.start();
        Thread.sleep(10);
        t2.start();
        Thread.sleep(10);
        t3.start();
    }
}
