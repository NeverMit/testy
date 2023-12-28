package potoki;

import static java.lang.Thread.currentThread;
import static java.util.stream.IntStream.range;

public class Runner {
    private static final int CREATED_THREADS_AMOUNT=10;
    public static void main(String[] args) {
        /*System.out.println(currentThread().getName());
        final Thread thread1=new MyThread();
        thread1.start();
        final Thread thread2=new Thread(){
          @Override
          public void run(){
              System.out.println(currentThread().getName()+" Создано с помощью анонимного класса");
          }
        };
        thread2.start();
        final Runnable task=()-> System.out.println(currentThread().getName()+" Создано с помощью Runnable");
        final Thread thread3=new Thread(task);
        thread3.start();
*/
        final Runnable taskDisplayingThreadName=()->  System.out.println(currentThread().getName()+
                " Какая-то конструкция, которая создает 10 потоков"+
                " Отпочковываемся от потока taskDisplayingThreadName");
        final Runnable taskCreatingThreads=()->
                range(0,CREATED_THREADS_AMOUNT)
                        .forEach(i->startThread(taskDisplayingThreadName));
        startThread(taskCreatingThreads);
    }
    private static void startThread(final Runnable runnable){
        final Thread thread=new Thread(runnable);
        thread.start();
    }
    private static final class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println(currentThread().getName()+" Создано с помощью extends Thread");
        }

    }
}
