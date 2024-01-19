package potoki.poolobjects;

public abstract class AbstractPoolTask<T> implements Runnable {
    private final AbstractPool<T> pool;
    public AbstractPoolTask(final AbstractPool<T> pool){
        this.pool=pool;
    }
    @Override
    public final void run(){
        final T object=this.pool.acquire();
        try{
            System.out.printf("%s was acquired\n",object);
            this.handle(object);
        }finally {
            System.out.printf("%s is being released\n",object);
            this.pool.release(object);
        }
    }
    protected abstract void handle(final T object);
}
