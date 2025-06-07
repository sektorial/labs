package lab8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Lab8 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> t1 = CompletableFuture.supplyAsync(wrap(1), executor);
        CompletableFuture<Integer> t2 = CompletableFuture.supplyAsync(wrap(2), executor);
        CompletableFuture<Integer> t3 = CompletableFuture.supplyAsync(wrap(3), executor);

        CompletableFuture<Void> all = CompletableFuture.allOf(t1, t2, t3);

        try {
            all.join();
        } catch (CompletionException ex) {
            System.err.println("One or more tasks failed: " + ex.getCause().getMessage());
        }

        t1.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.println("t1 error: " + ex.getCause().getMessage());
            } else {
                System.out.println("t1 result: " + res);
            }
        });
        t2.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.println("t2 error: " + ex.getCause().getMessage());
            } else {
                System.out.println("t2 result: " + res);
            }
        });
        t3.whenComplete((res, ex) -> {
            if (ex != null) {
                System.err.println("t3 error: " + ex.getCause().getMessage());
            } else {
                System.out.println("t3 result: " + res);
            }
        });

        executor.shutdown();
    }

    private static int longTask(int id) throws Exception {
        Thread.sleep(500L * id);
        if (id == 2) {
            throw new Exception("Task " + id + " failed");
        }
        return id * 10;
    }

    private static Supplier<Integer> wrap(int id) {
        return () -> {
            try {
                return longTask(id);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        };
    }
}
