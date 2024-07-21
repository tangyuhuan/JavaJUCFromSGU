package lock;

import java.util.concurrent.locks.ReentrantLock;

/**p7
 * 2.2.1 Lock接口：使用三部曲
 * 参考在线jdk文档：https://doc.qzxdp.cn/jdk/17/zh/api/java.base/java/util/concurrent/locks/ReentrantLock.html
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：创建多线程调用资源类的方法
 */
public class LSaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"AA").start();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"BB").start();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"CC").start();

    }
}

class LTicket {
    //票数量
    private int number = 30;
    //1.创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();
    private boolean soldout = false;
    //卖票方法
    public void sale() {
        //2.上锁
        lock.lock(); // block until condition holds
        try {
            if(number>0){
                System.out.println(Thread.currentThread().getName()+"售出一张票，剩下："+(--number));
            }else{
                soldout = true;
            }
        } finally {//finally中的一定会执行，确保锁的释放
            //3.解锁
            lock.unlock();
        }
    }

    public boolean isSoldout() {
        return soldout;
    }
}
