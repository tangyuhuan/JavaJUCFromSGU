package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**p11 线程间通信 Lock锁实现案例
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：在资源类操作方法（1）判断（2）干活 （3）通知
 * 第三步：创建多线程调用资源类的方法
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(()->{
            while(true){
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"AA").start();
        new Thread(()->{
            while(true){
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"BB").start();
        new Thread(()->{
            while(true){
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"CC").start();
        new Thread(()->{
            while(true){
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"DD").start();
        }
    }
//第一步：创建资源类，定义属性和操作方法
class Share{
    //初始值
    private int number;
    //创建lock锁
    private Lock lock = new ReentrantLock();
    //声明钥匙
    private Condition condition = lock.newCondition();
    //+1方法
    public void incr() throws InterruptedException {
//        第二步：在资源类操作方法（1）判断（2）干活 （3）通知

        //上锁
        lock.lock();
        try{
            //判断
            while(number != 0) {
                condition.await();//等待
            }
            //干活：如果number是0，就+1
            System.out.println(Thread.currentThread().getName()+" "+(++number));
            //通知其他线程
            condition.signalAll();
        }finally {
            //解锁
            lock.unlock();
        }

    }
    //-1方法
    public synchronized void decr() throws InterruptedException{
        //上锁
        lock.lock();
        try {
            //判断
            while(number != 1){ //判断number是否0，如果不是0，等待
                condition.await();//等待
            }
            //干活
            System.out.println(Thread.currentThread().getName() + " " + (--number));
            //通知其他线程
            condition.signalAll();
        }finally {
            //解锁
            lock.unlock();
        }
    }
}
