package com.mozhumz.usermanage.Thread;

import java.util.concurrent.*;

public class CallTask implements Callable
{
    public static ExecutorService pool= Executors.newFixedThreadPool(1);
    @Override
    public Object call() throws Exception {
        System.out.println("CallTask start...");
        throw new RuntimeException("CallTask-err");
    }

    public static void main(String[] args) {
//        pool.submit(new CallTask() {
//        });
        FutureTask futureTask=new FutureTask(new CallTask());
        pool.submit(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
