package potoki.poolobjects;

public class Runner {
    public static void main(String[] args) {
        final int poolSize=3;
        final ConnectionPool pool=new ConnectionPool(poolSize);
        final ConnectionPoolTask poolTask=new ConnectionPoolTask(pool);
        final int threadCount=15;
        final Thread[] threads=ThreadUtil.createThreads(poolTask,threadCount);
        ThreadUtil.startThreads(threads);
    }
}
