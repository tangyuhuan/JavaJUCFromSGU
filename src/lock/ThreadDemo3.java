package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**p13 线程间定制化通信
 * A线程打印5次A，B线程打印10次B，C线程打印15次C,按照此顺序循环10轮
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：在资源类操作方法（1）判断（2）干活 （3）通知
 * 第三步：创建多线程调用资源类的方法
 *
 * 定制化通信方式在于设定标志位置
 */

//第一步：创建资源类
class ShareResource{
    //定义标志位
    private int flag = 1;//1 AA  2 BB   3 CC

    public void setFlag(int flag) {
        this.flag = flag;
    }

    //创建lock锁
    private Lock lock = new ReentrantLock();
    //创建3把钥匙
    private Condition c1= lock.newCondition();
    private Condition c2= lock.newCondition();
    private Condition c3= lock.newCondition();
/*
    第二步：在资源类操作方法（1）判断（2）干活 （3）通知
*/
    //打印5次，参数第几轮
    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try{
            //（1）判断
            while(flag != 1){
                //等待
                c1.await();
            }
            //（2）干活
            for(int i = 0;i < 5;i++){
                System.out.println(Thread.currentThread().getName()+"打印第"+(i+1)+"次,轮数"+loop);
            }
            //（3）通知
            flag = 2;//修改标志位
            c2.signal();//通知BB线程
        }finally {
            lock.unlock();
        }
    }
    //打印10次，参数第几轮
    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try{
            //（1）判断
            while(flag != 2){
                //等待
                c2.await();
            }
            //（2）干活
            for(int i = 0;i < 10;i++){
                System.out.println(Thread.currentThread().getName()+"打印第"+(i+1)+"次,轮数"+loop);
            }
            //（3）通知
            flag = 3;//修改标志位
            c3.signal();//通知CC线程
        }finally {
            lock.unlock();
        }
    }
    //打印15次，参数第几轮
    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try{
            //（1）判断
            while(flag != 3){
                //等待
                c3.await();
            }
            //（2）干活
            for(int i = 0;i < 15;i++){
                System.out.println(Thread.currentThread().getName()+"打印第"+(i+1)+"次,轮数"+loop);
            }
            //（3）通知
            flag = 1;//修改标志位
            c1.signal();//通知AA线程
        }finally {
            lock.unlock();
        }
    }
}


public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource sr = new ShareResource();

        new Thread(()->{
            for(int i = 0;i < 10;i++){
                try {
                    sr.print5(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"AA").start();
        new Thread(()->{
            for(int i = 0;i < 10;i++){
                try {
                    sr.print10(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"BB").start();
        new Thread(()->{
            for(int i = 0;i < 10;i++){
                try {
                    sr.print15(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"CC").start();
    }
}