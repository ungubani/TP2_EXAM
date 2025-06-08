//Suppose you are given the following code:
//
//class FooBar {
//    public void foo() {
//        for (int i = 0; i < n; i++) {
//            print("foo");
//        }
//    }
//
//    public void bar() {
//        for (int i = 0; i < n; i++) {
//            print("bar");
//        }
//    }
//}
//The same instance of FooBar will be passed to two different threads:
//
//thread A will call foo(), while
//thread B will call bar().
//Modify the given program to output "foobar" n times.
//
//
//
//Example 1:
//
//Input: n = 1
//Output: "foobar"
//Explanation: There are two threads being fired asynchronously. One of them calls foo(), while the other calls bar().
//        "foobar" is being output 1 time.
//        Example 2:
//
//Input: n = 2
//Output: "foobarfoobar"
//Explanation: "foobar" is being output 2 times.
//
//
//Constraints:
//
//1 <= n <= 1000

class FooBar {
    private final Object lock = new Object();
    private int orderInt;
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized(lock) {
                while (orderInt % 2 != 0 && orderInt < 2 * n) {
                    lock.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
        	    printFoo.run();
                ++orderInt;
                lock.notify();
            }

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized(lock) {
                while (orderInt % 2 != 1 && orderInt < 2 * n) {
                    lock.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
        	    printBar.run();
                ++orderInt;
                lock.notify();
            }

        }
    }
}