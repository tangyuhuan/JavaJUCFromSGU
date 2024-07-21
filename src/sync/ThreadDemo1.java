package sync;

/**p9 线程间通信 synchronized方式实现 AA 1,BB 0,AA 1,BB 0.....的效果
 * p10 虚假唤醒问题
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
        /*if(number != 0){ //判断number是否0，如果不是0，等待
            this.wait();//虚假唤醒:wait在哪里睡，就在哪里醒，醒后之前判断无效，就从这里开始接着往下执行
        }*/
        //虚假唤醒问题解决方案：写到while循环中，进行循环唤醒判断（无论何时醒来都要经过while条件）
        while(number != 0) {
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