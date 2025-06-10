

public class IncrementTest {
    public static void main(String[] args) {
        int[] intValue = {0};
        int COUNT = 1_00_000_000;

        IncrementWorkerThread threadOne = new IncrementWorkerThread(intValue, COUNT);
        IncrementWorkerThread threadTwo = new IncrementWorkerThread(intValue, COUNT);

        threadOne.start();
        threadTwo.start();
        try {
            // System.out.println(threadOne.getValue());
            // System.out.println(threadTwo.getValue());
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(intValue[0]);
        // System.out.println(intValue.length);
    }

}

class IncrementWorkerThread extends Thread {
    int[] integerValue;
    int countRepeat;
    public IncrementWorkerThread(int[] intValue, int count) {
        integerValue = intValue;
        countRepeat = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < countRepeat; ++i) {
            synchronized (integerValue) {
                integerValue[0] += 1;
            }
        }
    }

    public int getValue() {
        synchronized (integerValue) {
            return integerValue[0];
        }
    }
}
