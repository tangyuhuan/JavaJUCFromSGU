package juc;

import java.util.concurrent.CountDownLatch;

/**p26 JUC-辅助类（减少计数CountDownLatch）
 * CountDownLatch类可以设置一个计数器，然后通过countDown方法来进行减1的操作，
 * 使用await方法等待到计数器不大于0时，继续执行await方法之后的语句。
 *
 * 使用场景：6个同学都离开教室后，班长才可以锁门
 * @author tyh
 * @version 1.0
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //改造前：同学没走完，班长已经把门锁了
/*        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" 号同学离开了教室");
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()+"班长锁门");*/

//        改造后：使用CountDownLatch计数器实现6个同学都离开教室后，班长才可以锁门
        //创建CountDownLatch对象，设置初始值
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //6个同学陆续离开教室之后
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" 号同学离开了教室");
                //计数-1
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        //等待（当计数器值没有变成0，就一直等待，变成0因await方法阻塞的线程会被唤醒，继续执行）
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"班长锁门");

    }
}
