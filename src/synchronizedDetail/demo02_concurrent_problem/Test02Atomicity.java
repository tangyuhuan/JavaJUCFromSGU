package synchronizedDetail.demo02_concurrent_problem;

import java.util.ArrayList;
import java.util.List;

/**p8 08_synchronized保证原子性
 * 添加14、19行代码，利用synchronized保证原子性
 * @author tyh
 * @version 1.0
 */
public class Test02Atomicity {
    //1.定义一个共享变量number
    private static int number = 0;
    private static Object obj = new Object();//08_synchronized保证原子性
    public static void main(String[] args) throws InterruptedException {
        //2.对number进行1000次的++操作
       Runnable runnable = ()->{
           for (int i = 0; i < 1000; i++) {
               synchronized (obj) {//08_synchronized保证原子性
                   number++;
               }
           }
       };
//       为了必须要5个线程执行完再去取number的值，用join操作让其插队
        List<Thread> list = new ArrayList<>();
       //3.使用5个线程来进行
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            list.add(thread);
        }
        for(Thread thread:list){
            thread.join();
        }
//      有可能主线程跑得更快，5个for循环还没跑完就去取number的值了
        System.out.println("number = " + number);//多执行几次，number不一定为5000
    }
}
