package readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**p30 读写锁：案例实现
 * 模拟缓存过程（多线程向map中放数据、取数据）演示读写锁基本操作
 * 理想中的应该是写完之后才能读，而没加锁时：没有写完就开始读，读是读不到的
 * ————解决方案，添加读写锁ReentrantReadWriteLock进行改造
 * @author tyh
 * @version 1.0
 */
//资源类
class MyCache{
    //volatile:
    //创建map集合
    private volatile Map<String,Object> map = new HashMap<>();
    //1.创建读写锁对象
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    //放数据
    public void put(String key,Object value){
        //2.添加写锁
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"正在写操作"+key);
            //暂停一会儿
            TimeUnit.MICROSECONDS.sleep(300);
            //放数据
            //注意：为保证写锁能有效锁住，写的过程要放在释放写锁之前
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"写完了"+key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //3.释放写锁
            rwlock.writeLock().unlock();
        }
    }
    //取数据
    public Object get(String key){
        //4.添加读锁
        rwlock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName()+"正在读取操作"+key);
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取完了"+key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //5.释放读锁
            rwlock.readLock().unlock();
        }
        return result;
    }
}
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //创建线程放数据
        for(int i=1;i<=5;i++){
            int num = i;
            new Thread(()->{
                myCache.put(num+"",num+"");
            },String.valueOf(i)).start();
        }
        try {
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //创建线程取数据
        for(int i=1;i<=5;i++){
            int num = i;
            new Thread(()->{
                myCache.get(num+"");
            },String.valueOf(i)).start();
        }
    }
}
/*
根据执行结果，也可以看出写锁是独占锁（一个线程写完释放，下一个线程写、释放），读锁是共享锁（多个线程可以同时去读）
3正在写操作3
3写完了3
1正在写操作1
1写完了1
5正在写操作5
5写完了5
4正在写操作4
4写完了4
2正在写操作2
2写完了2
1正在读取操作1
2正在读取操作2
3正在读取操作3
4正在读取操作4
5正在读取操作5
2读取完了2
5读取完了5
1读取完了1
3读取完了3
4读取完了4
*/

