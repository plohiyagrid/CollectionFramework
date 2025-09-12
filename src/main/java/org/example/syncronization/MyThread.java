package org.example.syncronization;

public class MyThread extends Thread{
     protected Counter counter;

     public MyThread(Counter counter){
         this.counter = counter;
     }

     @Override
     public void run(){
         for (int i = 0; i < 1000; i++) {
             counter.increment();
         }
     }
}
