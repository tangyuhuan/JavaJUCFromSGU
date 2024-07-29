package juc;

/**wait 和 sleep 方法的不同之
 * 区别五：线程进入状态不同：调用 sleep 方法和有参wait方法，
 * 线程会进入 TIMED_WAITING 有时限等待状态，而调用无参数的 wait 方法，线程会进入 WAITING 无时限等待状态
 * @author tyh
 * @version 1.0
 */
public class SleepAndWait3 {
    public static void main(String[] args) throws InterruptedException {
        //关键字synchronized来与对象的互斥锁联系，当某个对象用synchronized修饰时，表明该对象在任一时刻只能由一个线程访问
        Object lock = new Object();
        Thread t1 = new Thread(()->{
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(()->{
            synchronized (lock) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t2.start();
        System.out.println("wait()之后进入的状态："+t1.getState());
        System.out.println("sleep(2000)之后进入的状态："+t2.getState());
    }
}
