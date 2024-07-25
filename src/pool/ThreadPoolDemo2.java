package pool;

import java.util.concurrent.*;

/**p40 自定义线程池创建
 * @author tyh
 * @version 1.0
 */
public class ThreadPoolDemo2 {
    public static void main(String[] args) {
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(2, 5, 2L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        maximumPoolSize5+阻塞队列3=8，最多同时可以同时执行8个线程任务，超出的执行拒绝策略java.util.concurrent.RejectedExecutionException
        try{
            for (int i = 0; i < 10; i++) {
                //执行
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"办理业务");
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            threadPoolExecutor.shutdown();
        }
    }
}
