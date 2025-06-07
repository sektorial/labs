package lab1;

import static lab1.Helper.displayThreadsState;

public class Task1_1_2_undaemon_thread {

    public static void main(final String[] args) {
        Thread thread = new Thread(new MyTask());
        thread.setDaemon(true);

        System.out.println("> Інстанційовано фоновий потік " + thread.getName());
        displayThreadsState();

        System.out.println("> Запускаємо фоновий потік " + thread.getName());
        thread.start();
        displayThreadsState();

        System.out.println("> Змінюємо фоновий потік " + thread.getName() + " на звичайний");
        try {
            thread.setDaemon(false);
        } catch (final IllegalThreadStateException exception) {
            System.out.println("Зміна флагу daemon у робочому потоці призводить до помилки: " + exception);
        }
    }

}


