package lab1;

public class Task1RunningVsStartingThread {

    public static void main(final String[] args) throws InterruptedException {
        final MyTask task = new MyTask();
        final Thread thread = new Thread(task);
        System.out.println("--- Виконання логіки без запуску нового потоку ---");
        System.out.println("Count до запуску: " + task.getCount());
        thread.run();
        System.out.println("Count після запуску: " + task.getCount());

        System.out.println("--- Відновлення count ---");
        task.resetCount();

        System.out.println("--- Виконання логіки через запуск нового потоку ---");
        System.out.println("Count до запуску: " + task.getCount());
        thread.start();
        for (var mainCount = 100; mainCount < 105; mainCount++) {
            System.out.println("Main Count: " + mainCount);
            Thread.sleep(200);
        }
        thread.join();
        System.out.println("Count після запуску: " + task.getCount());
    }
}


