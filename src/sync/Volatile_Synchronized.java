package sync;

/** volatile 和 synchronized 的区别
 * 可见性：指的是一个线程对共享变量的修改能够立即被其他线程观察到。‌
 * Java中的volatile关键字用于修饰变量，表示该变量可能会被多个线程同时访问并修改，因此需要保证该变量的可见性。
 * 使用volatile修饰的变量，每次读取时都会从主内存中读取，每次修改时都会立刻写回主内存，而不会使用本地缓存。这样能够保证不同线程之间对该变量的修改是可见的。
 * https://blog.csdn.net/weixin_44772566/article/details/137629937
 * @author tyh
 * @version 1.0
 */
public class Volatile_Synchronized {
    private volatile int i = 0;

    public void increase() {
        i++;
    }

    public int getI() {
        return i;
    }
    public static void main(String[] args) {
        /*volatile关键字适用于那些不需要进行原子性操作，但需要保证可见性的变量场景，比如标识线程状态的变量、双重检查锁等*/
        /* 示例，使用volatile保证i变量的可见性：*/
        Volatile_Synchronized example = new Volatile_Synchronized();
        // 线程1：不断增加i的值
        Thread thread1 = new Thread(() -> {
            for (int j = 0; j < 1000; j++) {
                example.increase();
            }
        });
        // 线程2：输出i的值
        Thread thread2 = new Thread(() -> {
            while (example.getI() < 1000) {
                // 可能会一直循环等待，直到i的值被线程1修改为1000
            }
            System.out.println("i的值为：" + example.getI());
        });

        // 启动线程
        thread1.start();
        thread2.start();
    }
}

/*在这个示例中，我们创建了一个VolatileExample类，其中包含一个volatile修饰的变量i。
在increase方法中，我们对i进行了增加操作；在getI方法中，我们返回i的当前值。
在main方法中，我们创建了两个线程，线程1负责不断增加i的值，线程2负责输出i的值。
由于i被volatile修饰，因此线程2可以正确地读取到线程1修改后的i的值，从而保证了变量i的可见性。*/

