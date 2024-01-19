package potoki.countdownlatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTask implements Runnable {
    private final long id;
    private final CountDownLatch latch;

    public ResourceTask(final long id, final CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }
    protected abstract void run(final CountDownLatch latch);
    @Override
    public final void run(){
        this.run(this.latch);
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"[id = "+this.id+"]";
    }
}
