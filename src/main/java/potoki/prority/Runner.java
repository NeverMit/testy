package potoki.prority;

import java.util.stream.IntStream;

public class Runner {
    private static final String MESSAGE_MAIN_THREAD_FINISHED="Main thread is finished";

    public static void main(String[] args) {
        final Thread thread=new Thread(new Task());
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        System.out.println(MESSAGE_MAIN_THREAD_FINISHED);
    }

    private static final class Task implements Runnable{
        private static final int RANGE_MINIMAL_BORDER=0;
        private static final int RANGE_MAXIMAL_BORDER=100;
        @Override
        public void run(){
            IntStream.range(RANGE_MINIMAL_BORDER,RANGE_MAXIMAL_BORDER).forEach(System.out::println);
        }
    }
}
