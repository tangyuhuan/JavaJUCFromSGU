package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**p11 线程间通信 Lock锁实现案例
 *
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：在资源类操作方法（1）判断（2）干活 （3）通知
 * 第三步：创建多线程调用资源类的方法
 *
 *
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
/*        new Thread(()->{
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
        },"DD").start();*/
        }
    }
//第一步：创建资源类，定义属性和操作方法
class Share{
    //初始值
    private int number;
    //创建lock锁，ReentrantLock()构建一个可用来保护临界区的可重入锁
    private Lock lock = new ReentrantLock();
    //声明钥匙
    //通常，线程进入临界区，却发现在某一条件满足之后他才能执行，需要一个条件对象来管理
    //Condition newCondition()返回一个与该锁相关的条件对象
    private Condition condition = lock.newCondition();
    //+1方法
    public void incr() throws InterruptedException {
//        第二步：在资源类操作方法（1）判断（2）干活 （3）通知

        //上锁
        lock.lock();
        try{
            //判断
            while(number != 0) {
                condition.await();//等待，将该线程放到条件的等待集中
                System.out.println("incrincrincrincrincrincrincrR");
            }
            //干活：如果number是0，就+1
            System.out.println("如果number是0");
            System.out.println(Thread.currentThread().getName()+" "+(++number));
            //通知其他线程
            condition.signalAll();//解除该条件等待集中所有线程的阻塞状态
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
