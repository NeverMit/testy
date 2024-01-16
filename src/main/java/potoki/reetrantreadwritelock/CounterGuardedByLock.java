package potoki.reetrantreadwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterGuardedByLock extends AbstractCounter{
    private Lock lock=new ReentrantLock();

    @Override
    protected Lock getReadLock() {
        return this.lock;
    }

    @Override
    protected Lock getWriteLock() {
        return this.lock;
    }
}
