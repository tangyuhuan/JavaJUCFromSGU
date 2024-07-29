package sync;

/**p9 线程间通信 synchronized方式实现 AA 1,BB 0,AA 1,BB 0.....的效果
 * p10 wait和notify虚假唤醒问题：一旦是用了wait和notify，就一定要把判断条件放在while中，而不是if中，否则会导致虚假唤醒
 * 关于虚假唤醒的视频讲解：https://www.bilibili.com/video/BV16U4y1D7Zm/?spm_id_from=333.337.search-card.all.click&vd_source=9096009d98e9575f0e391c12407212da
 * wait 方法必须配合 synchronized 一起使用，不然在运行时就会抛出 IllegalMonitorStateException 的异常
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：在资源类操作方法（1）判断（2）干活 （3）通知
 * 第三步：创建多线程调用资源类的方法
 */
public class ThreadDemo1 {
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
    //+1方法
    public synchronized void incr() throws InterruptedException {
//        第二步：在资源类操作方法（1）判断（2）干活 （3）通知
        //虚假唤醒问题
        //wait会释放锁
        /*if(number != 0){ //判断number是否0，如果不是0，等待
            this.wait();//虚假唤醒:wait()在哪里睡，就在哪里醒，醒后之前判断无效（判断 number 在之前就已经执行完成了），就从这里开始接着往下执行（被唤醒时就不会再进行判断 number 了）
        }*/
        //虚假唤醒问题解决方案：写到while循环中，进行循环唤醒判断（无论何时醒来都要经过while条件）
        //‌虚假唤醒是指在多线程环境中，当一个或多个线程由于某些条件被唤醒，但实际上这些条件并不满足，导致线程被“唤醒”但并未完成预期的任务。
        //使用while循环代替if语句：在检查条件是否满足时使用while循环，而不是if语句。这样，即使线程被虚假唤醒，它也会再次检查条件并可能再次进入等待状态，而不是立即执行后续操作。‌
        //使用while解决虚假唤醒：即74行wait醒了之后，75行因为是while，又会进行72行校验，满足条件又执行wait，就不会虚假唤醒了
        while(number != 0) {
            //无参数的wait方法，线程会进入 WAITING 无时限等待状态，永久休眠，直到另一个线程调用了 notify 或 notifyAll 之后，休眠的线程才能被唤醒。
            this.wait();
        }

        //如果number是0，就+1
        System.out.println(Thread.currentThread().getName()+" "+(++number));
        //通知其他线程
        this.notifyAll();
    }
    //-1方法
    public synchronized void decr() throws InterruptedException{
        while(number != 1){ //判断number是否0，如果不是0，等待
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+" "+(--number));
        //通知其他线程
        this.notifyAll();
    }
}