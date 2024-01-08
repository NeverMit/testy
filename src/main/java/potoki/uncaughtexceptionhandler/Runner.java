package potoki.uncaughtexceptionhandler;

public class Runner {
    private static final String MESSAGE_EXCEPTION_TEMPLATE="Exception was thrown with message '%s' in thread '%s'.\n";
    public static void main(String[] args) {
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler=(thread,exception)
                -> System.out.printf(MESSAGE_EXCEPTION_TEMPLATE,exception.getMessage(),thread.getName());

        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);

        final Thread firstThread=new Thread(new Task());
        firstThread.start();

        final Thread secondThread=new Thread(new Task());
        secondThread.start();
    }
    private static final class Task implements Runnable{
        private static final String EXCEPTION_MESSAGE="I'm exception";
        @Override
        public void run() {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }
}
