package lab1;

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
