package lab7;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Lab7 {
    public static void main(String[] args) {
        final int N = 10_000_000;
        final int CEILING = 30; // використовується рандом у [0..30] щоб fib вміщалося в int

        // Створюємо та заповнюємо масив елементів випадковими числами
        int[] input = new int[N];
        for (int i = 0; i < N; i++) {
            input[i] = ThreadLocalRandom.current().nextInt(CEILING);
        }

        // Попередньо обчислюємо масив, за яким визначатимемо fib
        int[] fibLookup = new int[CEILING];
        fibLookup[0] = 0;
        fibLookup[1] = 1;
        for (int i = 2; i < CEILING; i++) {
            fibLookup[i] = fibLookup[i - 1] + fibLookup[i - 2];
        }

        // Заповнюємо масив fib, використовуючи fibLookup
        Integer[] fib = new Integer[N];
        for (int i = 0; i < N; i++) {
            fib[i] = fibLookup[input[i]];
        }

        // Сортуємо у зростанні
        Arrays.parallelSort(fib);

        // Згруповуємо повтори у Map (лише ті з count > 1)
        Map<Integer, Long> counts = new LinkedHashMap<>();
        int prev = fib[0];
        long cnt = 1;
        for (int i = 1; i < N; i++) {
            if (fib[i] == prev) {
                cnt++;
            } else {
                if (cnt > 1) {
                    counts.put(prev, cnt);
                }
                prev = fib[i];
                cnt = 1;
            }
        }
        if (cnt > 1) {
            counts.put(prev, cnt);
        }

        // Вивід масиву без повторів
        System.out.println("Унікальні Fibonacci values:" + Arrays.stream(fib)
                .sorted()
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll));
        System.out.println();

        // Вивід кількості повторів
        System.out.println("Повторювані значення та їх кількість:");
        counts.forEach((value, frequency) ->
                System.out.println(value + ": " + frequency)
        );
    }
}
