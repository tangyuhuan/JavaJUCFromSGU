package synchronizedDetail.demo01_concurrent_problem;

/**p2 02_可见性问题
 * 可见性（Visibility）：是指一个线程对共享变量进行修改，另一个先立即得到修改后的最新值。
 * 案例演示：一个线程根据boolean类型的标记ﬂag， while循环，另一个线程改变这个ﬂag变量的值，另一个线程并不会停止循环。
 * 目标：演示可见性问题
 * 1.创建一个共享变量
 * 2.创建一条线程不断读取共享变量
 * 3.创建一条线程修改共享变量
 *
 * 多线程执行具有随机性，线程1、2先执行哪个都有可能
 * 输出“线程修改共享变量flag值为false”之后，程序还没有结束，上面的线程并没有立即得到最新的结果，while(true)故程序没有停止
 * @author tyh
 * @version 1.0
 */
public class Test01Visibility {
//    1.创建一个共享变量
    private static boolean flag = true;
//    2.创建一条线程不断读取共享变量
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while(flag){

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
