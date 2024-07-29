package pool;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**p37 使用方式和底层原理
 * 线程池中execute和submit都是线程池提交任务的方式
 * @author tyh
 * @version 1.0
 */
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        //一池五线程 newFixedThreadPool创建一个可重用固定线程数的线程池
        //用5个线程处理10个请求，能同时处理5个请求，第六个请求需要等待，若其他请求处理完成，就继续处理第六个（参考一银行N窗口）
        ExecutorService pool1 = Executors.newFixedThreadPool(5);
        try{
            //10个顾客请求
            for (int i = 0; i < 10; i++) {
                //执行
                pool1.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"pool1办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭
            pool1.shutdown();
        }

        //一池一线程 newSingleThreadExecutor
        //一个银行只有一个窗口
        ExecutorService pool2 = Executors.newSingleThreadExecutor();
        try{
            //10个顾客请求
            for (int i = 0; i < 10; i++) {
                //执行
                pool2.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"pool2办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭
            pool2.shutdown();
        }
        
        //一池可扩容线程
        //线程池根据需求创建线程，可扩容，遇强则强
        ExecutorService pool3 = Executors.newCachedThreadPool();
        try{
            //10个顾客请求
            for (int i = 0; i < 10; i++) {
                //执行
                pool3.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"pool3办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭
            pool3.shutdown();
        }
    }
}
