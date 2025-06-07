package lab1;

public class Task1_1_4 {

    public static void main(String[] args) throws InterruptedException {
        int n = 3;
        System.out.println("\t\tMain start");

        MyThread[] myThreads = new MyThread[n];
        for (int i = 0; i < n; i++) {
            myThreads[i] = new MyThread();
        }

        Thread[] newThrd = new Thread[n];
        for (int i = 0; i < n; i++) {
            final int idx = i;
            newThrd[i] = new Thread(() -> myThreads[idx].run(idx));
        }

        for (int i = 0; i < n; i++) {
            newThrd[i].start();
        }

        for (int i = 0; i < 20; i++) {
            System.out.println("\t\tMain ");
            Thread.sleep(7);
        }

        for (int i = 0; i < n; i++) {
            newThrd[i].join();
        }

        System.out.println("\t\tMain end");
    }
}

class MyThread {
    public int count = 0;

    public void run(int number) {
        System.out.println("Thread " + number + " is started.");
        do {
            try {
                Thread.sleep(2 + 5 * number);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(" Thread-" + number + " - " + count);
            count++;
        } while (count <= 10);

        System.out.println("\nThread " + number + " is finished.");
    }
}



