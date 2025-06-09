// Программа, которая находит минимальный элемент в массиве.
// Написать с использованием многопоточности.
// Программа должна работать так, чтобы в любой момент
// можно было обратиться к потоку и узнать минимум на текущий момент

package taskTwo;

import java.util.ArrayList;
import java.util.Random;


public class TaskTwo {
    public static void main(String[] args) {
        int size = 100_001;
        ArrayList<Integer> list = new ArrayList<>(size);

        int trueMin = Integer.MAX_VALUE;
        int randomInt;

        Random rand = new Random();

        for (int i = 0; i < size; ++i) {
            randomInt = 50_000 - rand.nextInt(100_000);
            trueMin = Math.min(trueMin, randomInt);
            list.add(randomInt);
        }

        System.out.println("TRUE MIN = " + trueMin);


        int multiMin = Integer.MAX_VALUE;
        WorkerThread threadOne = new WorkerThread(list, 0, list.size() / 2);
        WorkerThread threadTwo = new WorkerThread(list, list.size() / 2, list.size());

        threadOne.start();
        threadTwo.start();

        System.out.println("Минимум в 1м потоке в какой-то момент: " +
                threadOne.getMin());  // Вроде бы не должно быть проблем при чтении?!

        try {
            System.out.println("Минимум в 1м потоке в какой-то момент: " +
                    threadOne.getMin());  // Вроде бы не должно быть проблем при чтении?!

            //-------------
            // Первый поток
            //-------------
            threadOne.join();
            System.out.println("Минимум в 1м потоке после его завершения: " +
                    threadOne.getMin());
            multiMin = Math.min(multiMin, threadOne.getMin());

            //-------------
            // Второй поток
            //-------------
            threadTwo.join();
            multiMin = Math.min(multiMin, threadTwo.getMin());

            System.out.println("Минимум в 1м потоке после его завершения: " +
                    threadOne.getMin());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MULTI MIN = " + multiMin);
    }
}

class WorkerThread extends Thread {
    private int min = Integer.MAX_VALUE;
    private final ArrayList<Integer> list;
    private final int start;
    private final int end;

    public WorkerThread(ArrayList<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; ++i) {
            synchronized (this) {
                min = Math.min(min, list.get(i));
            }
        }
    }

    public synchronized int getMin() {
        return min;
    }
}