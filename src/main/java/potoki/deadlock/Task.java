package potoki.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public final class Task implements Runnable{
    private static final String MESSAGE_TEMPLATE_TRY_ACQUIRE_LOCK="Thread '%s' is trying acquire lock '%s'\n";
    private static final String MESSAGE_TEMPLATE_SUCCESS_ACQUIRE_LOCK="Thread '%s' acquired lock '%s'\n";
    private static final String MESSAGE_TEMPLATE_RELEASE_LOCK="Thread '%s' released lock '%s'\n";
    private static final String NAME_FIRST_LOCK="firstLock";
    private static final String NAME_SECOND_LOCK="secondLock";
    private final Lock firstLock,secondLock;
    public Task(final Lock firstLock,final Lock secondLock){
        this.firstLock=firstLock;
        this.secondLock=secondLock;
    }
    @Override
    public void run() {
        final String currentThreadName=Thread.currentThread().getName();
        System.out.printf(MESSAGE_TEMPLATE_TRY_ACQUIRE_LOCK,currentThreadName,NAME_FIRST_LOCK);
        this.firstLock.lock();
        try {
            System.out.printf(MESSAGE_TEMPLATE_SUCCESS_ACQUIRE_LOCK,currentThreadName,NAME_FIRST_LOCK);
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.printf(MESSAGE_TEMPLATE_TRY_ACQUIRE_LOCK,currentThreadName,NAME_SECOND_LOCK);
            this.secondLock.lock();
            try {
                System.out.printf(MESSAGE_TEMPLATE_SUCCESS_ACQUIRE_LOCK,currentThreadName,NAME_SECOND_LOCK);
            }finally {
                this.secondLock.unlock();
                System.out.printf(MESSAGE_TEMPLATE_RELEASE_LOCK,currentThreadName,NAME_SECOND_LOCK);
            }
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
        finally {
            this.firstLock.unlock();
            System.out.printf(MESSAGE_TEMPLATE_SUCCESS_ACQUIRE_LOCK,currentThreadName,NAME_FIRST_LOCK);
        }
    }
}
