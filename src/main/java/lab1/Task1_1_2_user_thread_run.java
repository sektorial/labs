package lab1;

import static lab1.Helper.displayThreadsState;

public class Task1_1_2_user_thread_run {

    public static void main(final String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyTask());

        System.out.println("> Інстанційовано звичайний потік " + thread.getName());
        displayThreadsState();

        System.out.println("> Запускаємо звичайний потік " + thread.getName());
        thread.start();
        displayThreadsState();

        System.out.println("> Ставимо на паузу основний потік " + Thread.currentThread().getName());
        Thread.sleep(150);
        displayThreadsState();

        System.out.printf("> Завершуємо %s. Потік %s виконається повністю, оскільки він не фоновий.\n",
                Thread.currentThread().getName(),
                thread.getName());
        displayThreadsState();
    }

}


