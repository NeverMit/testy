package potoki.volatilelesson;

import java.util.concurrent.TimeUnit;

public final class PrintingTask implements Runnable{
    private volatile boolean shouldPrint=true;
    public void setShouldPrint(final boolean shouldPrint){
        this.shouldPrint=shouldPrint;
    }

    @Override
    public void run() {
        try{
            while (this.shouldPrint){
                System.out.println("I am working");
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
}
