package lab1;

public class Task1RunningVsStartingThread {

    public static void main(final String[] args) throws InterruptedException {
        final MyTask task = new MyTask();
        final Thread thread = new Thread(task);
        System.out.println("--- Виконання логіки без запуску нового потоку ---");
        System.out.println("Count до запуску: " + task.getCount());
        thread.run();  // прямий виклик: виконується в потоці main
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
        thread.join();  // чекаємо завершення дочірнього потоку
        System.out.println("Count після запуску: " + task.getCount());
    }
}

class MyTask implements Runnable {
    private int count = 0;

    public void run() {
        System.out.println(">> Запущено MyTask");
        while (count <= 10) {
            System.out.println("\tCount: " + count);
            count++;
            try {
                Thread.sleep(150);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println(">> Завершено MyTask");
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }

}


