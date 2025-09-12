package org.example.syncronization;

public class Test {
    public static void main(String[] args) {
        Counter counter = new Counter();
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (Exception e){

        }

        System.out.println(counter.getCount());
        //ans <= 2000
        //both can read same value at some cases


        Counter counterSync = new Counter();
        SyncThread syncThread1= new SyncThread(counterSync);
        SyncThread syncThread2= new SyncThread(counterSync);
        syncThread1.start();
        syncThread2.start();
        try{
            syncThread1.join();
            syncThread2.join();
        }catch (Exception e){

        }
        System.out.println(counterSync.getCount());




    }
}
