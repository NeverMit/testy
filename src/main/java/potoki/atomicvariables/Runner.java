package potoki.atomicvariables;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {
        EvenNumberGenerator generator=new EvenNumberGenerator();

        final int taskGenerationCounts=10000;
        final Runnable generatingTask=()-> IntStream
                .range(0,taskGenerationCounts)
                .forEach(i-> generator.generate());

        final int amountOfGeneratingThreads=5;
        final Thread[] generatingThreads=createThreads(generatingTask,amountOfGeneratingThreads);

        startThreads(generatingThreads);
        waitUntilFinish(generatingThreads);

        final int expectedGeneratorValue=amountOfGeneratingThreads*taskGenerationCounts*2;
        final int actualGeneratorValue=generator.getValue();
        if(expectedGeneratorValue!=actualGeneratorValue){
            throw new RuntimeException(
              "Expected is %d but was %d".formatted(expectedGeneratorValue,actualGeneratorValue)
            );
        }
    }
    private static void waitUntilFinish(final Thread[] threads){
        Arrays.stream(threads).forEach(Runner::waitUntilFinish);
    }
    private static void waitUntilFinish(final Thread thread){
        try {
            thread.join();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
    private static Thread[] createThreads(final Runnable task,final int amountOfThreads){
        return IntStream
                .range(0,amountOfThreads)
                .mapToObj(i->new Thread(task))
                .toArray(Thread[]::new);
    }
    private static void startThreads(final Thread[] threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
