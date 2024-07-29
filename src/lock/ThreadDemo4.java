package lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**p14 集合线程安全-异常演示：线程安全是指在多线程环境下,多个线程同时访问同一资源时,不会产生意外结果或导致数据出错的状态。
 * 线程不安全即当有多个线程同时对集合进行修改时，会出现java.util.ConcurrentModificationException异常
 * p17 集合线程安全-HashSet和HashMap线程不安全
 * 1.ArrayList集合线程不安全
 * 2.HashSet集合线程不安全
 * 3.HashMap集合线程不安全
 * @author tyh
 * @version 1.0
 * list集合线程不安全：ArrayList并没有synchronized关键词，是线程不安全的
 * 多个线程一边往里存、一边往里取，就会出现并发修改问题
 * 多运行几次会出现Exception in thread "2" java.util.ConcurrentModificationException
 */

public class ThreadDemo4 {
    public static void main(String[] args) {
       /* 1.ArrayList集合线程不安全*/
/*        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(list);//这行产生异常，并发修改问题
            },String.valueOf(i)).start();
        }*/
        /*2.HashSet集合线程不安全*/
/*        Set<String> set = new HashSet<>();
        //创建10个线程进行读写
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                set.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(set);//这行产生异常，并发修改问题
            },String.valueOf(i)).start();
        }*/

        /*HashSet集合线程不安全——————可以通过线程安全类CopyOnWriteArraySet解决*/
/*        Set<String> set1 = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //从集合中加入内容
                set1.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合中获取内容
                System.out.println(set1);//这行产生异常，并发修改问题
            },String.valueOf(i)).start();
        }*/

        /*3.HashMap集合线程不安全*/
/*        HashMap<String,String> hashMap = new HashMap<>();
        //创建10个线程进行读写
        for (int i = 0; i < 10; i++) {
            //在Lambdas中使用的局部变量必须是Final或Effectively Final，Effectively Final是指那些虽然没有被声明为final，但是在使用过程中并没有被修改的局部变量。
            //Effectively Final是指那些虽然没有被声明为final，但是在使用过程中并没有被修改的局部变量
            //key是Effectively Final变量
            String key = String.valueOf(i);
            new Thread(() -> {
                //从集合中加入内容
                hashMap.put(key,UUID.randomUUID().toString().substring(0, 8));
                *//*i不是Final或Effectively Final，这种写法不可以*//*
                *//*hashMap.put(String.valueOf(i),UUID.randomUUID().toString().substring(0, 8));*//*
                //从集合中获取内容
                System.out.println(hashMap);//这行产生异常，并发修改问题
            },String.valueOf(i)).start();
        }*/

        /*HashMap集合线程不安全——————可以通过线程安全类ConcurrentHashMap解决*/
        Map<String,String> hashMap = new ConcurrentHashMap<>();
        //创建10个线程进行读写
        for (int i = 0; i < 10; i++) {
            //在Lambdas中使用的局部变量必须是Final或Effectively Final，Effectively Final是指那些虽然没有被声明为final，但是在使用过程中并没有被修改的局部变量。
            //Effectively Final是指那些虽然没有被声明为final，但是在使用过程中并没有被修改的局部变量
            //key是Effectively Final变量
            String key = String.valueOf(i);
            new Thread(() -> {
                //从集合中加入内容
                hashMap.put(key,UUID.randomUUID().toString().substring(0, 8));
                //*i不是Final或Effectively Final，这种写法不可以*//*
                //*hashMap.put(String.valueOf(i),UUID.randomUUID().toString().substring(0, 8));*//*
                //从集合中获取内容
                System.out.println(hashMap);//这行产生异常，并发修改问题
            },String.valueOf(i)).start();
        }
    }
}
