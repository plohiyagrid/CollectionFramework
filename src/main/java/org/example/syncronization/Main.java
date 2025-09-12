package org.example.syncronization;

public class Main {
    public static void main(String[] args) {
        Runnable taskLock = getRunnable();

//        Thread t1 = new Thread(task , "Thread 1");
//        //using sync can create infinite lock as t1 will not free CS till task is executed
//        Thread t2 = new Thread(task , "Thread 2");
//        t1.start();
//        t2.start();

        Thread t1 = new Thread(taskLock , "Thread 1");
        //using sync can create infinite lock as t1 will not free CS till task is executed
        Thread t2 = new Thread(taskLock , "Thread 2");
        t1.start();
        t2.start();


    }

    private static Runnable getRunnable() {
        BankAccount bankAccount = new BankAccount(100);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    bankAccount.withdraw(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        return new Runnable() {
            @Override
            public void run() {
                try {
                    bankAccount.withdrawLock(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
