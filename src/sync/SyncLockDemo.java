package sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**p20 p21 可重入锁
 * java中的可重入锁是一种递归无阻塞的同步机制，允许线程多次获得同一个锁
 * synchronized和lock都是可重入锁，synchronized是隐式的（自动上锁和解锁），lock是显示的（需要手动上锁和解锁）
 * 方式一：synchronized 通过两个场景演示：
 * 场景一：通过synchronized 同步代码块，锁定对象，可自由进入内部区域
 * 场景二：同步方法，可重入锁（用的是一把锁）可以实现循环递归调用
 *
 *方式二：lock演示可重入锁
 * @author tyh
 * @version 1.0
 */
public class SyncLockDemo {
    /*场景二：同步方法，可重入锁（用的是一把锁）可以实现循环递归调用*/
    public synchronized void add(){
        add();
    }
    public static void main(String[] args) {
        //抛出StackOverflowError异常，说明是可重入锁（用的是一把锁）所以可以循环递归调用
//        new SyncLockDemo().add();//抛出StackOverflowError异常

        /*场景一：通过synchronized 同步代码块，锁定对象，可自由进入内部区域*/
        Object o = new Object();
        new Thread(()->{
            synchronized (o){
                System.out.println(Thread.currentThread().getName()+"外层");
                synchronized (o){
                    System.out.println(Thread.currentThread().getName()+"中层");
                    synchronized (o){
                        System.out.println(Thread.currentThread().getName()+"内层");
                    }
                }
            }
        },"T1").start();

        /*lock演示可重入锁*/
        Lock lock = new ReentrantLock();
        //创建线程
        new Thread(()->{
            try{
                //上锁
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"外层");
                try{
                    //上锁
                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+"内层");
                }finally {
                    //释放锁
//                    lock.unlock();
                }
            }finally {
                //释放锁
                lock.unlock();
            }
        },"T2").start();

        //55行不释放锁，导致66行无法拿到锁，代码无法执行
        new Thread(()->{
            //上锁
            lock.lock();
            System.out.println("aaaaa");
            //释放锁
            lock.unlock();
        },"aaaa").start();
    }
}
