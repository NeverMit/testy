package potoki.state;

public class Runner {
    private static final String MESSAGE_TEMPLATE_THREAD_STATE="%s : %s\n";
    public static void main(String[] args) throws InterruptedException {
        final Thread mainThread=Thread.currentThread();
        final Thread thread=new Thread(()-> {
            try {
                mainThread.join();
                showThreadState(Thread.currentThread());
            }catch (InterruptedException e){

            }
        });
        thread.start();
        Thread.sleep(1000);
        showThreadState(thread);

    }
    private static void showThreadState(final Thread thread){
        System.out.printf(String.format(MESSAGE_TEMPLATE_THREAD_STATE,thread.getName(),thread.getState()));
    }
}
