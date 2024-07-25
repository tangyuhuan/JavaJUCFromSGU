package readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**p32 读写锁的降级
 * @author tyh
 * @version 1.0
 */
public class Demo1 {
    public static void main(String[] args) {
        //可重入读写锁对象
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();

        //演示锁降级过程（写锁降级成读锁）：获取写锁、获取读锁、释放写锁、释放读锁
/*
        // 读的时候不能写，但写操作可以读，如果把20-21放开，27-28注释，执行会卡在“get readLock”——证明了读的时候不能写，除非在写之前释放读锁
        //2.获取读锁
        readLock.lock();
        System.out.println("get readLock");*/

        //1.获取写锁
        writeLock.lock();
        System.out.println("get writeLock");
        //2.获取读锁
        readLock.lock();
        System.out.println("get readLock");
        //3.释放写锁
        writeLock.unlock();
        //4.释放读锁
        readLock.unlock();

    }
}
