package juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**p27 JUC-辅助类（信号灯Semaphore）
 * Semaphore 的构造方法中传入的第一个参数是最大信号量（可以看成最大线程池），每个信号量初始化为一个最多只能分发一个许可证。
 * 使用 acquire 方法获得许可证，release 方法释放许可
 * 使用场景：6辆汽车，停3个车位
 * @author tyh
 * @version 1.0
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //1.创建Semaphore，设置许可数量3，
        Semaphore semaphore = new Semaphore(3);
        //模拟6辆汽车
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                //抢占车位
                try {
                    //2.获得许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到了车位");

/*                    TimeUnit是java.util.concurrent包下的一个类名
                    主要功能是暂停线程的操作
                    与Thread.sleep()一样的功能都是暂停线程, 但因为有很多枚举变量，更有可读性
                    NANOSECONDS	毫微秒、MILLISECONDS	毫秒、SECONDS 秒、MINUTES	分钟等等*/

                    //设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));//new Random().nextInt (50)代表生成的随机数范围是0-50

                    System.out.println(Thread.currentThread().getName()+"------离开了车位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    //3.释放许可
                    semaphore.release();
                }

            },String.valueOf(i)).start();
        }
    }
}