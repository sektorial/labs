package lab2;

public class Lab2_Child {
    public static void main(String[] args) throws InterruptedException {
        int counter = 0;
        while (true) {
            System.out.println(counter++);
            Thread.sleep(500);
        }
    }
}
