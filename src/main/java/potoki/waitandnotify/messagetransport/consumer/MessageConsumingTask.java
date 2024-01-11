package potoki.waitandnotify.messagetransport.consumer;

import potoki.waitandnotify.messagetransport.broker.MessageBroker;
import potoki.waitandnotify.messagetransport.model.Message;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class MessageConsumingTask implements Runnable{
    private static final int SECONDS_DURATION_TO_SLEEP_BEFORE_CONSUMING= 1;
    private final MessageBroker messageBroker;
    private final int minimalAmountMessagesToConsume;
    private final String name;
    public MessageConsumingTask(final MessageBroker messageBroker,
                                final int minimalAmountMessagesToConsume,final String name){
        this.messageBroker=messageBroker;
        this.minimalAmountMessagesToConsume=minimalAmountMessagesToConsume;
        this.name=name;
    }
    public int getMinimalAmountMessagesToConsume(){
        return this.minimalAmountMessagesToConsume;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public void run() {
       try {
           while (!Thread.currentThread().isInterrupted()){
               TimeUnit.SECONDS.sleep(SECONDS_DURATION_TO_SLEEP_BEFORE_CONSUMING);
               final Optional<Message> optionalConsumedMessage=this.messageBroker.consume(this);
               optionalConsumedMessage.orElseThrow(MessageConsumingException::new);
           }
       }catch (final InterruptedException interruptedException){
           Thread.currentThread().interrupt();
       }
    }
}
