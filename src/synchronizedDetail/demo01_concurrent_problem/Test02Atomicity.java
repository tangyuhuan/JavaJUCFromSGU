package synchronizedDetail.demo01_concurrent_problem;

import java.util.ArrayList;
import java.util.List;

/**p3 03_原子性问题
 * 原子性（Atomicity）：在一次或多次操作中，要么所有的操作都执行并且不会受其他因素干扰而中断，要么所有的操作都不执行。
 * 并发编程时，会出现原子性问题，当一个线程对共享变量操作到一半时，另外的线程也有可能来操作共享变量，干扰了前一个线程的操作。
 *
 * number++是由多条语句组成，以上多条指令在一个线程的情况下是不会出问题的，但是在多线程情况下就可能会出现问题。
 * 比如一个线程在执行13: iadd时，另一个线程又执行9: getstatic。会导致两次number++，实际上只加了1。
 * 
 * 目标：演示原子性问题
 * 1.定义一个共享变量number
 * 2.对number进行1000次的++操作
 * 3.使用5个线程来进行
 * @author tyh
 * @version 1.0
 */
public class Test02Atomicity {
    //1.定义一个共享变量number
    private static int number = 0;
    public static void main(String[] args) throws InterruptedException {
        //2.对number进行1000次的++操作
       Runnable runnable = ()->{
           for (int i = 0; i < 1000; i++) {
               number++;
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
