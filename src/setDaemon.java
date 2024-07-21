/**P4
 * 1.6 用户线程和守护线程
 * @author tyh
 * @version 1.0
 */
public class setDaemon {
    public static void main(String[] args) {
/*        1.主线程结束了，用户线程还在running,JVM存活*/
/*        new Thread(()->{
            System.out.println("用户线程"+"running");
            while(true){

            }
        },"aa").start();*/
/*        2.设置守护线程, 如果没有用户线程,都是守护线程,JVM结束*/
        Thread bb = new Thread(()->{
            System.out.println("用户线程"+"running");
            while(true){

            }
        },"bb");
        bb.setDaemon(true);
        bb.start();
        System.out.println("主线程running");
    }
}