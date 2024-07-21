package callable;

import java.util.concurrent.*;

/**p23 创建线程的第三种方式：Callable接口概述
 * @author tyh
 * @version 1.0
 * Callable接口实现多线程
 * Callable 接口是一个返回结果的接口，有两种使用方式：
 * 1.通常与 Future 接口和 Executor 接口组合使用，实现异步执行任务。
 * 2.利用Runnable接口实现类FutureTask，实现异步执行任务。
 * FutureTask构造可以传递Callable，从而建立起Callable和Runnable的关系
 * FutureTask(Callable<V> callable) 创建一个 FutureTask ，它将在运行时执行给定的 Callable 。
 *
 */
public class Demo1 {
    public static void main(String[] args) {
        /*1.通常与 Future 接口和 Executor 接口组合使用，实现异步执行任务。*/
/*        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Integer> task = () -> {
            // 这里是你的任务代码
            System.out.println("Callable task is running.");
            return 42; // 返回计算结果
        };

        Future<Integer> future = executor.submit(task);

        try {
            System.out.println("The result is: " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }*/

        /*2.利用Runnable接口实现类FutureTask，实现异步执行任务。*/
        //Callable接口是FunctionalInterface，可以使用lambda表达式
        FutureTask futureTask = new FutureTask(() -> {
            // 这里是你的任务代码
            System.out.println(Thread.currentThread().getName()+" come in callable");
            return 42; // 返回计算结果
        });
        FutureTask futureTask2 = new FutureTask(() -> {
            // 这里是你的任务代码
            System.out.println(Thread.currentThread().getName()+" come in callable");
            return 42; // 返回计算结果
        });
        /*FutureTask原理 未来任务
        在不影响主线程的情况下，单独开启一个任务，最后进行汇总（汇总一次）
         */
/*        1.老师上课，口渴了，去买水不合适，讲课线程继续
        单开线程找班长帮我买水，把水买回来，需要时候直接get

        2.4个同学，1同学1+2+。。+5，2同学10+11+12+。。。+50，3同学60+61+62，4同学100+200
            第2个同学计算量比较大
            FutureTask单开线程给2同学计算，先汇总1 3 4，最后等2同学计算完成，统一汇总
            汇总一次：已经计算过的，就不用再重复算了

        3.考试时，先做会做的题目，最后看不会做的题目
        */

        //创建一个线程
        new Thread(futureTask,"lucy").start();
        new Thread(futureTask2,"marry").start();
        while(!futureTask.isDone()){
//            System.out.println("wait");
        }
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread().getName()+" end");//main end

    }

}
