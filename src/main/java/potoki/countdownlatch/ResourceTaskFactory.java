package potoki.countdownlatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTaskFactory {
    private long nextId;
    protected abstract ResourceTask create(final long id, final CountDownLatch latch);
    public final ResourceTask create(final CountDownLatch latch){
        return create(this.nextId++,latch);
    }
}
