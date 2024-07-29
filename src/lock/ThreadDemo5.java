package lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**p14 集合线程安全-ArrayList线程不安全和解决方案（一）
 * p14 集合线程安全-ArrayList线程不安全和解决方案（二）
 * @author tyh
 * @version 1.0
 * list集合线程不安全：ArrayList并没有synchronized关键词，是线程不安全的
 * 多个线程一边往里存、一边往里取，就会出现并发修改问题
 * 多运行几次会出现Exception in thread "2" java.util.ConcurrentModificationException
 *
 * 解决方案1；用Vector替代ArrayList,不过这是个古老的方案，Vector jdk1.0就有了，很古老，实际用的不多
 * 解决方案2: Collections工具类的synchronizedList(List<T> list)方法,返回由指定列表支持的同步（线程安全）列表,也很古老，实际用的不多
 * 解决方案3: JUC（包java.util.concurrent）中的CopyOnWriteArrayList类，实际常用推荐
 */

public class ThreadDemo5 {

    public static void main(String[] args) {
/*
        解决方案1；用Vector替代ArrayList,不过这是个古老的方案，Vector jdk1.0就有了
*/
//        List<String> list = new ArrayList<>();
        List<String> list = new Vector<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(list);
            },String.valueOf(i)).start();
        }
/*
        解决方案2: Collections工具类的synchronizedList(List<T> list)方法,返回由指定列表支持的同步（线程安全）列表。
        任何集合通过使用同步包装器synchronizedList wrapper变成线程安全的
*/
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                list2.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(list2);
            },String.valueOf(i)).start();
        }

/*      解决方案3: JUC（包java.util.concurrent）中的CopyOnWriteArrayList，实际常用推荐*/
        List<String> list3 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                list3.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(list3);
            },String.valueOf(i)).start();
        }
    }
}
