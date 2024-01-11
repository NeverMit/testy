package potoki.lock.Base;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {
        final EvenNumberGenerator evenNumberGenerator=new EvenNumberGenerator();

        final Runnable generatingTask=()-> IntStream.range(0,100).
                forEach(i-> System.out.println(evenNumberGenerator.generate()));

        final Thread firstThread=new Thread(generatingTask);
        firstThread.start();

        final Thread secondThread=new Thread(generatingTask);
        secondThread.start();

        final Thread thirdThread=new Thread(generatingTask);
        thirdThread.start();
    }
    private static final class EvenNumberGenerator{ //генератор четных чисел

        private final Lock lock;
        private int previousGenerated;

        public EvenNumberGenerator() {
            this.lock=new ReentrantLock();
            this.previousGenerated = -2;
        }
        public int generate(){
            return this.lock.tryLock() ? this.onSuccessAcquireLock() : this.onFailAcquireLock();
        }
        private int onSuccessAcquireLock(){
            try {
                return this.previousGenerated+=2;
            }finally {
                this.lock.unlock();
            }
        }
        private int onFailAcquireLock(){
            System.out.printf("Thread '%s' didn't acquire lock.\n",Thread.currentThread().getName());
            throw new RuntimeException();
        }
    }
}
