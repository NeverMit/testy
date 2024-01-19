package potoki.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class ResourceLoader extends ResourceTask {
    private final long secondDuration;

    public ResourceLoader(final long id, final CountDownLatch latch, final long secondDuration) {
        super(id, latch);
        this.secondDuration = secondDuration;
    }

    @Override
    protected void run(CountDownLatch latch) {
        try {
            System.out.printf("%s is loading some resource\n",this);
            TimeUnit.SECONDS.sleep(this.secondDuration);
            System.out.printf("Some resource was loaded by %s\n",this);
            latch.countDown();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
}
