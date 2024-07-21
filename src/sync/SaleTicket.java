package sync;

/**P6
 * 2.1 Synchronized
 * @author tyh
 * @version 1.0
 * 多线程编程步骤
 * 第一步：创建资源类，定义属性和操作方法
 * 第二步：创建多线程调用资源类的方法
 */

public class SaleTicket {
    public static void main(String[] args) {
//        第二步：创建多线程调用资源类的方法
        Ticket ticket = new Ticket();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"AA").start();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"BB").start();
        new Thread(()->{
            while(!ticket.isSoldout()){
                ticket.sale();
            }
        },"CC").start();
    }
}
//第一步：创建资源类，定义属性和操作方法
class Ticket{
    //票数量
    private int number = 30;
    private boolean soldout = false;
    //卖票方法
    public synchronized void sale() {
        if(number>0){
            System.out.println(Thread.currentThread().getName()+"售出一张票，剩下："+(--number));
        }else{
            soldout = true;
        }
    }

    public boolean isSoldout() {
        return soldout;
    }
}