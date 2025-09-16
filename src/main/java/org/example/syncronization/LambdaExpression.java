package org.example.syncronization;

public class LambdaExpression {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello : " + Thread.currentThread().getName());
        //Automatically overwrite run method -> as it is functional interfaces
        Thread t1 = new Thread(runnable);

        Thread t2 = new Thread(() -> {
            System.out.println("Hello : " + Thread.currentThread().getName());
        });
        t1.start();
        t2.start();


    }
}
