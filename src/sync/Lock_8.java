package sync;

import java.util.concurrent.TimeUnit;

/**p18 演示synchronized锁的8个问题！！重要
 * @author tyh
 * @version 1.0
 */

/*资源类Phone*/
class Phone {
    public static synchronized void sendSMS() throws Exception {
        //停留4秒
        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }
    public /*static*/ synchronized void sendEmail() throws Exception {
        System.out.println("------sendEmail");
    }
    public void getHello() {
        System.out.println("------getHello");
    }
}

/**
 * @Description: 8锁
 *
1 标准访问，先打印短信还是邮件
------sendSMS
------sendEmail


2 停4秒在短信方法内，先打印短信还是邮件
------sendSMS
------sendEmail
synchronized实现同步的基础：java中的每一个对象都可以作为锁
一个对象里面如果有多个 synchronized 方法，某一个时刻内，只能有唯一一个线程去访问这些
synchronized 方法锁的是当前对象 this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized 方法

3 新增普通的hello方法，是先打短信还是hello
------getHello
------sendSMS
没加锁的肯定先执行

4 现在有两部手机，先打印短信还是邮件
------sendEmail
------sendSMS
synchronized锁的是当前对象，两个对象不是同一把锁

5 两个静态同步方法，1部手机，先打印短信还是邮件 ??
------sendSMS
------sendEmail

6 两个静态同步方法，2部手机，先打印短信还是邮件
------sendSMS
------sendEmail

7 1个静态同步方法(sendSMS),1个普通同步方法(sendEmail)，1部手机，先打印短信还是邮件
------sendEmail
------sendSMS
楼（static）有锁，楼里每个房间有锁(sendEmail)
虽然楼锁住了，但是每个房间还可以进入————用的不是同一把锁

8 1个静态同步方法(sendSMS),1个普通同步方法(sendEmail)，2部手机，先打印短信还是邮件
------sendEmail
------sendSMS

 */

public class Lock_8 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(()->{
            try {
//                phone.getHello();
                phone.sendSMS();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        },"AA").start();
        Thread.sleep(100);
        new Thread(()->{
            try {
//                phone.sendEmail();
                phone2.sendEmail();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        },"BB").start();


    }
}
