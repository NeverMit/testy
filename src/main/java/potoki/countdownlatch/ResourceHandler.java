package potoki.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ResourceHandler extends ResourceTask{
    public ResourceHandler(final long id,final CountDownLatch latch) {
        super(id, latch);
    }

    @Override
    protected void run(final CountDownLatch latch) {
        try {
            latch.await();
            System.out.printf("Resources were handled by %s\n",this);
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
}
