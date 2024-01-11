package potoki.waitandnotify.messagetransport.producer;

import potoki.waitandnotify.messagetransport.broker.MessageBroker;
import potoki.waitandnotify.messagetransport.model.Message;

import java.util.concurrent.TimeUnit;

public final class MessageProducingTask implements Runnable {

    private static final int SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING= 1;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;
    private final int maximalAmountMessagesToProduce;
    private final String name;

    public MessageProducingTask(final MessageBroker messageBroker,final MessageFactory messageFactory,
                                final int maximalAmountMessagesToProduce,final String name){
        this.messageBroker=messageBroker;
        this.messageFactory=messageFactory;
        this.maximalAmountMessagesToProduce =maximalAmountMessagesToProduce;
        this.name=name;
    }

    public int getMaximalAmountMessagesToProduce() {
        return this.maximalAmountMessagesToProduce;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                final Message producedMessage = this.messageFactory.create();
                TimeUnit.SECONDS.sleep(SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING);
                this.messageBroker.produce(producedMessage,this);
            }
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }


}
