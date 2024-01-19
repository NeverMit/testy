package potoki.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {
        final int resourcesCount=3;
        final CountDownLatch latch=new CountDownLatch(resourcesCount);

        final ResourceLoaderFactory loaderFactory=new ResourceLoaderFactory();
        final Thread[] loadingThreads=createResourceThreads(loaderFactory,resourcesCount,latch);

        final ResourceHandlerFactory handlerFactory=new ResourceHandlerFactory();
        final int handlingThreadsCount=4;
        final Thread[] handlingThreads=createResourceThreads(handlerFactory,handlingThreadsCount,latch);

        ThreadUtil.startThreads(loadingThreads);
        ThreadUtil.startThreads(handlingThreads);
    }
    private static Thread[] createResourceThreads(final ResourceTaskFactory factory,
                                                  final int threadsCount,
                                                  final CountDownLatch latch){
        return IntStream.range(0,threadsCount)
                .mapToObj(i->factory.create(latch))
                .map(Thread::new)
                .toArray(Thread[]::new);
    }

}
