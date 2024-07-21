package sync;

import java.util.concurrent.TimeUnit;

/**p22 多线程锁-死锁
 * 演示死锁：线程A获取到锁a，但是睡眠了没释放锁a，线程B获取到锁b，睡眠了1秒
 * 不添加sleep的话，不一定能模拟出来死锁
 * 因为线程start时间不确定，一个线程直接获取锁a、锁b执行完后 下一个线程start，就没有死锁了
 * @author tyh
 * @version 1.0
 */
public class DeadLock {
    //创建两个对象
    static Object a = new Object();
    static Object b = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (a) {
                System.out.println("持有锁A，试图获取锁B");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    System.out.println("获取锁B");
                }
            }
        },"A").start();
        new Thread(()->{
            synchronized (b) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("持有锁B，试图获取锁A");
                synchronized (a) {
                    System.out.println("获取锁A");
                }
            }
        },"B").start();
    }
}
