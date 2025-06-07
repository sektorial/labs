package lab1;

import static lab1.Helper.displayThreadsState;

public class Task1_1_2_daemon_thread_run {

    public static void main(final String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyTask());
        thread.setDaemon(true);

        System.out.println("> Інстанційовано фоновий потік " + thread.getName());
        displayThreadsState();

        System.out.println("> Запускаємо фоновий потік " + thread.getName());
        thread.start();
        displayThreadsState();

        System.out.println("> Ставимо на паузу основний потік " + Thread.currentThread().getName());
        Thread.sleep(150);
        displayThreadsState();

        System.out.printf("> Завершуємо %s без завершення %s\n", Thread.currentThread().getName(), thread.getName());
        displayThreadsState();
    }

}


