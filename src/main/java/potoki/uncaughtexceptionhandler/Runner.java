package potoki.uncaughtexceptionhandler;

import java.util.concurrent.ThreadFactory;

public class Runner {
    private static final String MESSAGE_EXCEPTION_TEMPLATE="Exception was thrown with message '%s' in thread '%s'.\n";
    public static void main(String[] args) throws InterruptedException {
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler=(thread,exception)
                -> System.out.printf(MESSAGE_EXCEPTION_TEMPLATE,exception.getMessage(),thread.getName());

        final ThreadFactory threadFactory=new DaemonThreadWithUncaughtExceptionHandler(
                uncaughtExceptionHandler);

        final Thread firstThread=threadFactory.newThread(new Task());
        firstThread.start();

        final Thread secondThread=threadFactory.newThread(new Task());
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
    private static final class Task implements Runnable{
        private static final String EXCEPTION_MESSAGE="I'm exception";
        @Override
        public void run() {
            System.out.println(Thread.currentThread().isDaemon());
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
    private static final class DaemonThreadWithUncaughtExceptionHandler implements ThreadFactory{
        private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        public DaemonThreadWithUncaughtExceptionHandler(final Thread.UncaughtExceptionHandler uncaughtExceptionHandler){
            this.uncaughtExceptionHandler=uncaughtExceptionHandler;
        }
        @Override
        public Thread newThread(final Runnable runnable) {
            Thread thread=new Thread(runnable);
            thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
            thread.setDaemon(true);
            return thread;
        }
    }
}
