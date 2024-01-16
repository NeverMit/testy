package potoki.reetrantreadwritelock;

import java.util.concurrent.locks.Lock;

public abstract class AbstractCounter {
    private long value;
    protected abstract Lock getReadLock();
    protected abstract Lock getWriteLock();
    public final long getValue(){
        final Lock lock=this.getReadLock();
        lock.lock();
        try {
            return this.value;
        }finally {
            lock.unlock();
        }
    }
    public final void increment(){
        final Lock lock=this.getWriteLock();
        lock.lock();
        try {
            this.value++;
        }finally {
            lock.unlock();
        }
    }
}
