package org.example.syncronization;

//Collections of pre initialized threads to be used
//Better resource management
//Response time improvement
//Controlled threads creation

import java.util.concurrent.*;

// Executors Framework -> Java 5
// Executor , ExecutorService ,

public class ThreadPooling {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        ExecutorService executorServiceCached = Executors.newCachedThreadPool(); //Dynamic pools
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.submit(
                    () -> {
                        long result = factorial(finalI);
                        System.out.println("Factorial of " + finalI + " = " + result);
                    }
            );
        }

        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                System.out.println("Waiting Cached...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            System.out.println("Time taken : " + (System.currentTimeMillis() - startTime));
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorServiceCached.submit(
                    () -> {
                        long result = factorial(finalI);
                        System.out.println("Factorial of " + finalI + " using Cached pool = " + result);
                    }
            );
        }

        executorServiceCached.shutdown();
        try {
            while (!executorServiceCached.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                System.out.println("Waiting...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            System.out.println("Time taken using Cached pool: " + (System.currentTimeMillis() - startTime));
        }



//        if(executorService.isShutdown()) System.out.println("Time taken using isShutdown: " + (System.currentTimeMillis() - startTime));

//        try {
//            while(!executorService.isTerminated()) {
//                System.out.println("Waiting for termination..");
//            }
//            System.out.println("Time taken using isTerminated: " + (System.currentTimeMillis() - startTime));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        //Future ->
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService1.submit(() -> 1); //callable method
        if (future.isDone()){
            System.out.println("Completed..");
        }
        System.out.println(future.get());
        if (future.isDone()){
            System.out.println("Completed..");
        }
        executorService1.shutdown();


    }

    private static long factorial(int n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}


// Callable has a return type
//runnable has no return type
