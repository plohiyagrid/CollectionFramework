package org.example.syncronization;

public class SyncThread extends MyThread{


    public SyncThread(Counter counter){
        super(counter);
    }

    @Override
    public void run(){
        for (int i = 0; i < 1000; i++) {
//            counter.incrementSync();
            counter.incrementSyncBlock();

            //Both works same
        }
    }
}