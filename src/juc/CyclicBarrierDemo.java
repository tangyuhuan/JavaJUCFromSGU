package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**p27 JUC-辅助类（循环栅栏CyclicBarrierDemo）
 * CyclicBarrier：Cyclic循环，Barrier屏障，达到循环屏障后才执行后面的代码。
 * CyclicBarrier的构造方法第一个参数是目标障碍数，每次执行CyclicBarrier一次障碍数会加一，
 * 只有达到了目标障碍数，才会执行XXXXX，否则就一直出于等待状态
 * 使用场景：集齐7颗龙珠就可以召唤神龙
 * @author tyh
 * @version 1.0
 */
public class CyclicBarrierDemo {
    //新建固定值，定义神龙召唤需要的龙珠总数
    private final static int NUMBER = 7;
    public static void main(String[] args) {
        //创建CyclicBarrier循环栅栏对象，参数包含固定值+达到固定值之后需要做的事情
        //当给定数量的参与方（线程）等待它时，它将触发，并在触发障碍时执行给定的障碍操作，由进入障碍的最后一个线程执行。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER,()->{
            System.out.println("****集齐7颗龙珠就可以召唤神龙***");
        });
        //定义7个线程分别去收集龙珠
        for (int i = 1; i <= 7; i++) {
            new Thread(()->{
                //如果没有集齐七颗龙珠，就处于等待状态
                try {
                    System.out.println(Thread.currentThread().getName()+"星龙被收集到了");
                    cyclicBarrier.await();//每次执行CyclicBarrier一次障碍数会加一，如果达到了目标障碍数，才会执行cyclicBarrier.await()之后的语句。
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },String.valueOf(i)).start();
        }
    }

}
