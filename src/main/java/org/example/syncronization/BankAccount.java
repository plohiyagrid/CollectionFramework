package org.example.syncronization;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccount() {
        this.balance = 0;
    }

    public BankAccount(int balance) {
        this.balance = balance;
    }
    // lock.lock() -> synchronized
    public synchronized void withdraw(int amount) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount) ;
        if(balance >= amount){
            System.out.println(Thread.currentThread().getName() + " proceeding with withdrawal");
            Thread.sleep(3000);
            balance -= amount;
        } else {
            System.out.println(Thread.currentThread().getName() + " insufficient balance");
        }
    }

    public void withdrawLock(int amount) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        try {
            if (lock.tryLock(5000, TimeUnit.MILLISECONDS)) {
                if (balance >= amount) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " proceeding with withdrawal");
                        Thread.sleep(3000); // Simulate time taken to process the withdrawal
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: " + balance);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " insufficient balance");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquire the lock, will try later");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
