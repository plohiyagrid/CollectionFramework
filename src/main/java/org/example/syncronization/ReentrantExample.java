package org.example.syncronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private final Lock lock = new ReentrantLock();
    //This should cause deadlock but as java implemented Reentrant Lock
    //This should work as same thread acquired lock will reacquire it
    public void outerMethod() throws InterruptedException {
//        lock.lock();
        lock.lockInterruptibly();
        // lock.lockInterruptibly() -> this will allow locks to be interrupted by CPU calls
        try {
            System.out.println("Outer Method");
            innerMethod();
        } finally {
            lock.unlock();
        }
    }

    public void innerMethod(){
        lock.lock();
        try {
            System.out.println("Inner Method");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantExample example = new ReentrantExample();
        example.outerMethod();
    }
}
