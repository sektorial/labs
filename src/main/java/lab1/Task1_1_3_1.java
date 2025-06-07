package lab1;

public class Task1_1_3_1 {

    public static void main(final String[] args) {
        Thread thread = new Thread(() -> count(4));
        thread.start();
        for (int i = 1; i <= 10; i++) {
            System.out.println("Основний потік: " + (i * i));
            sleep(300);
        }
    }

    static void count(int number) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("\tНаш потік: " + (i * number));
            sleep(400);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

}


