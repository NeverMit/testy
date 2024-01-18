package potoki.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
    public static void main(String[] args) {
        final Lock firstGivenLock=new ReentrantLock();
        final Lock secondGivenLock=new ReentrantLock();

        final Thread firstGivenThread=new Thread(new Task(firstGivenLock,secondGivenLock));
        final Thread secondGivenThread=new Thread(new Task(secondGivenLock,firstGivenLock));

        firstGivenThread.start();
        secondGivenThread.start();
    }
}
