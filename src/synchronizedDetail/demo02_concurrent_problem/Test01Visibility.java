package synchronizedDetail.demo02_concurrent_problem;

/**p9 09_synchronized保证可见性
 * 可见性（Visibility）：是指一个线程对共享变量进行修改，另一个先立即得到修改后的最新值。
 * 案例演示：一个线程根据boolean类型的标记ﬂag， while循环，另一个线程改变这个ﬂag变量的值，另一个线程并不会停止循环。
 * 输出“线程修改共享变量flag值为false”之后，程序还没有结束，上面的线程并没有立即得到最新的结果，while(true)故程序没有停止
 * 解决办法：28-30行synchronized
 * @author tyh
 * @version 1.0
 */
public class Test01Visibility {
//    1.创建一个共享变量
    private static boolean flag = true;
    //如果用volatile也是可以的：当变量加volatile之后，同步回主内存后，缓存一致性协议，将其他工作内存中的该值都设置为失效，使得其他线程必须重新从主内存读取
    //private static volatile boolean flag = true;
    private static Object obj = new Object();//08_synchronized保证原子性
//    2.创建一条线程不断读取共享变量
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while(flag){
                //用System.out.println也能拿到被更新为false的flag，因为println方法有用synchronized (this)
                System.out.println(flag);
                synchronized (obj) {
                    //synchronized执行时对应lock原子操作，如果对一个变量执行lock操作，将会清空工作内存中此变量的值
                    //拿到被更新为false的flag
                }
        }
        }).start();
        Thread.sleep(2000);
//     3.创建一条线程修改共享变量
        new Thread(()->{
            flag = false;
            System.out.println("线程修改共享变量flag值为false");
        }).start();
    }
}
