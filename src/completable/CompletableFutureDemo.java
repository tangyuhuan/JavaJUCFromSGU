package completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**p43 JUC异步回调
 *
 * @author tyh
 * @version 1.0
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步调用,没有返回值
        //主线程里面创建一个CompletableFuture，然后主线程调用get方法会阻塞
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        completableFuture1.get();//主线程调用get方法阻塞
        //异步调用,有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            //模拟异常
            int i = 1/0;
            return 1024;
        });
        completableFuture2.whenComplete((result, throwable) -> {
            System.out.println(result);//方法的返回值
            System.out.println(throwable);
        }).get();
    }
}
