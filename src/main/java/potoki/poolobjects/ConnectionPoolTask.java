package potoki.poolobjects;

import java.util.concurrent.TimeUnit;

public class ConnectionPoolTask extends AbstractPoolTask<Connection>{
    public ConnectionPoolTask(final AbstractPool<Connection> pool) {
        super(pool);
    }

    @Override
    protected void handle(final Connection connection) {
        try {
            connection.setAutoCommit(false);
            TimeUnit.SECONDS.sleep(3);
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
}
