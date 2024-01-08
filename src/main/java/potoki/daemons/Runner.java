package potoki.daemons;

import java.util.concurrent.TimeUnit;

public class Runner {
    private static final String MESSAGE_MAIN_THREAD_FINISHED="Main thread is finished";
    public static void main(String[] args) {
        final Thread thread=new Thread(new Task());
        thread.setDaemon(true);
        thread.start();
        System.out.println(thread.isDaemon());
        System.out.println(MESSAGE_MAIN_THREAD_FINISHED);
    }
    private static final class Task implements Runnable{
        private static final String MESSAGE="I'm working";
        private static final int DURATION_BETWEEN_MESSAGE_IN_SECONDS=2;
        @Override
        public void run(){
            try {
                while (true){
                    System.out.println(MESSAGE);
                    TimeUnit.SECONDS.sleep(DURATION_BETWEEN_MESSAGE_IN_SECONDS);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }
    }
}
