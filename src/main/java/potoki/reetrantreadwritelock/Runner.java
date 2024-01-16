package potoki.reetrantreadwritelock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
//171629016-Amount of readings value with simple Lock
//22973195-Amount of readings value with ReadWriteLock
public class Runner {
    public static void main(String[] args) throws InterruptedException {
        testCounter(CounterGuardedReadWriteLock::new);
    }
    private static void testCounter(final Supplier<? extends AbstractCounter> counterFactory) throws InterruptedException {
        final AbstractCounter counter=counterFactory.get();

        final int amountOfThreadGettingValue=50;
        final ReadingValueTask[] readingValueTasks=createReadingTasks(counter,amountOfThreadGettingValue);
        final Thread[] readingValueThreads=mapToThreads(readingValueTasks);

        final Runnable incrementingCounterTask=createIncrementingCounterTask(counter);
        final int amountOfThreadsIncrementingCounter=2;
        final Thread[] incrementingCounterThreads=createThreads(
                incrementingCounterTask,
                amountOfThreadsIncrementingCounter
        );
        startThreads(readingValueThreads);
        startThreads(incrementingCounterThreads);

        TimeUnit.SECONDS.sleep(5);

        interruptThreads(readingValueThreads);
        interruptThreads(incrementingCounterThreads);

        waitUntilFinish(readingValueThreads);
        final long totalAmountOfReads=findTotalAmountOfReads(readingValueTasks);
        System.out.printf("Amount of readings value: "+totalAmountOfReads );
    }
    private static long findTotalAmountOfReads(final ReadingValueTask[] tasks){
        return Arrays.stream(tasks)
                .mapToLong(ReadingValueTask::getAmountOfReads)
                .sum();
    }
    private static void waitUntilFinish(final Thread[] threads){
        forEach(threads,Runner::waitUntilFinish);
    }
    private static void waitUntilFinish(final Thread thread){
        try {
            thread.join();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
    private static void interruptThreads(final Thread[] threads){
        forEach(threads,Thread::interrupt);
    }
    private static void startThreads(final Thread[] threads){
        forEach(threads,Thread::start);
    }
    private static void forEach(final Thread[] threads, final Consumer<Thread> action){
        Arrays.stream(threads).forEach(action);
    }
    private static Thread[] createThreads(final Runnable task,final int amountOfThread){
        return IntStream.range(0,amountOfThread)
                .mapToObj(i->new Thread(task))
                .toArray(Thread[]::new);
    }
    private static ReadingValueTask[] createReadingTasks(final AbstractCounter counter,final int amountOfTasks){
        return IntStream.range(0,amountOfTasks).
                mapToObj(i->new ReadingValueTask(counter))
                .toArray(ReadingValueTask[]::new);
    }
    private static Thread[] mapToThreads(final Runnable[] tasks){
        return Arrays.stream(tasks)
                .map(Thread::new)
                .toArray(Thread[]::new);
    }
    private static Runnable createIncrementingCounterTask(final AbstractCounter counter){
        return ()->{
          while (!Thread.currentThread().isInterrupted()){
              incrementCounter(counter);
          }
        };
    }
    private static void incrementCounter(final AbstractCounter counter){
        try {
            counter.increment();
            TimeUnit.SECONDS.sleep(1);
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
    private final static class ReadingValueTask implements Runnable{
        private final AbstractCounter counter;
        private long amountOfReads;
        public ReadingValueTask(final AbstractCounter counter){
            this.counter=counter;
        }
        public long getAmountOfReads(){
            return this.amountOfReads;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                this.counter.getValue();
                this.amountOfReads++;
            }
        }
    }
}
