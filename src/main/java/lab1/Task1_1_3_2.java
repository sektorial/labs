package lab1;

public class Task1_1_3_2 {

    public static void main(String[] args) {
        Counter counter = new Counter(5, 4);
        Thread thread = new Thread(() -> count(counter));
        thread.start();
    }

    static void count(Counter counter) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Second thread: " + (i * counter.x * counter.y));
            sleep(400);
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

}

class Counter {
    int x, y;

    Counter(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


