package potoki.reetrantreadwritelock;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class AbstractCounter {
    private long value;
    protected abstract Lock getReadLock();
    protected abstract Lock getWriteLock();
    public final OptionalLong getValue(){
        final Lock lock=this.getReadLock();
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            return OptionalLong.of(this.value);
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
            return OptionalLong.empty();
        }
        finally {
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
