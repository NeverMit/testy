package potoki.volatilelesson;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        final PrintingTask printingTask=new PrintingTask();
        final Thread printingThread=new Thread(printingTask);
        printingThread.start();
        TimeUnit.SECONDS.sleep(5);
        printingTask.setShouldPrint(false);
        System.out.println("Printing task should be stopped");
    }
}
