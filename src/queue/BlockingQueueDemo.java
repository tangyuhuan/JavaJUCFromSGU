package queue;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**p35 阻塞队列-核心方法演示
 * @author tyh
 * @version 1.0
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //创建定长的阻塞队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        queue.add("a");
        queue.add("b");
        queue.add("c");
        System.out.println(queue.element());//检索但不删除此队列的头部。此方法与 peek 的不同之处仅在于，如果此队列为空，它会抛出异常。返回：这个队列的头
//        当队列元素满时，再往队列插入元素会抛出IllegalStateException: Queue full
//        queue.add("d");
        System.out.println(queue.remove());//检索并删除此队列的头部。此方法与 poll() 的不同之处仅在于，如果此队列为空，它会抛出异常。
        System.out.println(queue.remove());
        System.out.println(queue.remove());
//        当队列为空时，再往队列里移除元素会抛出 NoSuchElementException
//        System.out.println(queue.remove());

//        第二组
        BlockingQueue<String> queue2 = new ArrayBlockingQueue<String>(3);
        System.out.println(queue2.offer("a"));
        System.out.println(queue2.offer("b"));
        System.out.println(queue2.offer("c"));
        System.out.println(queue2.offer("d"));//插入方法，成功true失败false
        System.out.println(queue2.poll());
        System.out.println(queue2.poll());
        System.out.println(queue2.poll());
        System.out.println(queue2.poll());//移除方法，成功返回队列元素，队列没有返回null


//        第三组
        BlockingQueue<String> queue3 = new ArrayBlockingQueue<String>(3);
        queue3.put("a");
        queue3.put("b");
        queue3.put("c");
//        queue3.put("d");//put方法：当阻塞队列满时，继续往队列里put元素，队列会一直阻塞生产者线程直到put数据or响应中断退出

        System.out.println(queue3.take());
        System.out.println(queue3.take());
        System.out.println(queue3.take());
//        System.out.println(queue3.take());//take方法，当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用

//        第四组
        BlockingQueue<String> queue4 = new ArrayBlockingQueue<String>(3);
        queue4.offer("a");
        queue4.offer("b");
        queue4.offer("c");
        queue4.offer("c",3L, TimeUnit.SECONDS);//（元素，阻塞时间，时间单位）当阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时后生产着线程退出

    }
}
