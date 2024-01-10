package potoki.racecondition;

import static java.util.stream.IntStream.range;

public class Runner {
    private static int counter=0;

    private static final int INCREMENT_AMOUNT_FIRST_THREAD=500;
    private static final int INCREMENT_AMOUNT_SECOND_THREAD=600;

    public static void main(String[] args) throws InterruptedException {
        final Thread firstThread=createIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_THREAD);
        final Thread secondThread=createIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_THREAD);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        System.out.println(counter);
    }
    private static Thread createIncrementingCounterThread(final int incrementAmount){
        return new Thread(()->
                range(0,incrementAmount).forEach(i->incrementCounter())
        );
    }
    private static synchronized void incrementCounter(){
        counter++;
    }
}
