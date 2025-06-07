package lab1;

import java.util.Comparator;

public final class Helper {

    private Helper() {
    }

    public static void displayThreadsState() {
        Thread.getAllStackTraces().keySet().stream()
                .filter(thread -> thread.getName().contains("main") || thread.getName().contains("Thread"))
                .sorted(Comparator.comparing(Thread::getName))
                .forEach(thread ->
                        System.out.printf("--> Стан потоку %s: %s\n", thread.getName(), thread.getState()));
    }
}
