package lab1;

public class Task1_1_3_3 {

    public static void main(String[] args) {
        Counter counter = new Counter(5, 4);
        Thread thread = new Thread(counter::count);
        thread.start();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    private static class Counter {
        int x, y;

        Counter(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void count() {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Second thread: " + (i * x * y));
                sleep(400);
            }
        }
    }

}




