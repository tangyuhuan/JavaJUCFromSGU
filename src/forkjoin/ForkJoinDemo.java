package forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**p42 分支合并框架-案例实现??不懂
 * 例子：）0+1+2+3+。。。+100，利用分支合并框架进行拆解，满足每个子任务为：差值不超过10的两个数相加
 * 代码参考：https://doc.qzxdp.cn/jdk/17/zh/api/java.base/java/util/concurrent/RecursiveTask.html
 * @author tyh
 * @version 1.0
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建拆分任务的MyTask对象
        MyTask myTask = new MyTask(0,100);
        //创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        //获取最终合并之后结果
        Integer result = forkJoinTask.get();
        System.out.println(result);
        //关闭池对象
        forkJoinPool.shutdown();
    }
}
class MyTask extends RecursiveTask<Integer> {
    //拆分差值不能超过10,计算10以内的运算
    private static final Integer Value = 10;
    private int begin;//拆分开始值
    private int end;//拆分结束值
    private int result;//返回结果

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    //实现拆分&&合并逻辑
    @Override
    protected Integer compute() {
        //判断相加的两个值是否大于10
        if(end - begin <= Value){
            //相加操作
            for(int i = begin; i <= end; i++){
                result += i;
            }
        }else{
            //进一步拆分:找到中间值，左边部分拆分，右边部分拆分
            int middle = (begin+end)/2;
            //拆分左边
            MyTask task01 = new MyTask(begin, middle);
            //拆分右边
            MyTask task02 = new MyTask(middle+1, end);
            //调用方法拆分
            task01.fork();//Fork方法的实现原理：当我们调用ForkJoinTask的fork方法时，程序会把任务放在ForkJoinWorkerThread的pushTask的workQueue中，异步地执行这个任务，然后立即返回结果
            task02.fork();
            //合并结果
            result = task01.join() + task02.join();//Join方法的主要作用是阻塞当前线程并等待获取结果
        }
       return result;
    }
}