// Программа, которая находит сумму всех элементов массива.
// Написать с использованием многопоточности.

package taskOne;

import java.util.ArrayList;
import java.util.Random;


public class taskOne {
    public static void main(String[] args) {
        int size = 10_001;
        ArrayList<Integer> list = new ArrayList<>(size);

        int trueSumm = 0;
        int randomInt;

        Random rand = new Random();

        for (int i = 0; i < size; ++i) {
            randomInt = rand.nextInt(1000);
            trueSumm += randomInt;
            list.add(randomInt);
        }

        System.out.println("TRUE SUMM = " + trueSumm);


        int multiSumm = 0;
        WorkerThread threadOne = new WorkerThread(list, 0, list.size() / 2);
        WorkerThread threadTwo = new WorkerThread(list, list.size() / 2, list.size());

        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            multiSumm += threadOne.getSumm();

            threadTwo.join();
            multiSumm += threadTwo.getSumm();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Multi SUMM = " + multiSumm);
    }
}

class WorkerThread extends Thread {
    private int Summ;
    private final ArrayList<Integer> list;
    private final int start;
    private final int end;

    public WorkerThread(ArrayList<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end =end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; ++i) {
            Summ += list.get(i);
        }
    }

    public int getSumm() {
        return Summ;
    }
}
