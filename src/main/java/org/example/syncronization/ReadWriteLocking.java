package org.example.syncronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLocking {
    private int count = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void increment(){
        writeLock.lock();
        try{
            count++;
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount(){
        readLock.lock();
        try{
            return count;
        }finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLocking counter = new ReadWriteLocking();

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " get Count : " + counter.getCount());
                    } catch (Exception ignored) {

                    }
                }
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        counter.increment();
                        System.out.println(Thread.currentThread().getName() + " increment");
                    }
                } catch (Exception ignored){

                }
            }
        };

        Thread writeThread1 = new Thread(writeTask);
//        Thread writeThread2 = new Thread(writeTask);
        Thread readThread1 = new Thread(readTask);
        Thread readThread2 = new Thread(readTask);

        writeThread1.start();
//        writeThread2.start();
        readThread1.start();
        readThread2.start();



        writeThread1.join();
        readThread1.join();
//        writeThread2.join();
        readThread2.join();

        System.out.println(counter.getCount());
    }
}
