package juc;

/**wait 和 sleep 方法的不同之
 * 区别四：释放锁资源不同：wait 方法会主动的释放锁，而 sleep 方法则不会
 * 执行结果显示：当调用了 wait 之后，主线程立马尝试获取锁成功了，这就说明 wait 休眠时是释放锁的。
 * @author tyh
 * @version 1.0
 */
public class SleepAndWait2 {
    public static void main(String[] args) throws InterruptedException {
        //关键字synchronized来与对象的互斥锁联系，当某个对象用synchronized修饰时，表明该对象在任一时刻只能由一个线程访问
        Object lock = new Object();
        new Thread(()->{
            synchronized (lock) {
                System.out.println("线程A获取到锁");
                try {
                    lock.wait(2000);
                    System.out.println("线程A释放锁");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Thread.sleep(200);
        System.out.println("主线程尝试获取锁");
        synchronized (lock) {
            System.out.println("主线程获取到锁");
        }

    }
}
