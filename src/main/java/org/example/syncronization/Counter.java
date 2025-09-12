package org.example.syncronization;

public class Counter {

     private int count = 0 ;

     public void increment(){    // Critical section -> shared resource
                                 // To prevent race condition we should use concept of mutual exclusion
         count++;
     }

     public synchronized void incrementSync(){ //we are in sync, but we don't have control on who is using resource
        count++;
     }

    public synchronized void incrementSyncBlock(){
         synchronized (this){
             count++;
         }
    }

     public int getCount(){
         return count;
     }

}

// Locks are of two types -> Intrinsic and Explicit
// 1. Intrinsic -> Automatic by using synchronised Keyword
// 2. Explicit -> Manual Locks using Lock Class
