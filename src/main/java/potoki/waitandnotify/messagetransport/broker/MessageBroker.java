package potoki.waitandnotify.messagetransport.broker;

import potoki.waitandnotify.messagetransport.consumer.MessageConsumingTask;
import potoki.waitandnotify.messagetransport.model.Message;
import potoki.waitandnotify.messagetransport.producer.MessageProducingTask;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;



public final class MessageBroker {
    private static final String MESSAGE_OF_MESSAGE_IS_PRODUCE="Message '%s' is produced by producer '%s'."
            + "Amount of messages before producing: '%d'.\n";
    private static final String TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMED="Message '%s' is consumed by consumer '%s'."
            + "Amount of messages before consuming: '%d'.\n";
    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;
    public MessageBroker(final int maxStoredMessages){
        this.maxStoredMessages=maxStoredMessages;
        this.messagesToBeConsumed=new ArrayDeque<>(maxStoredMessages);
    }
    public synchronized void produce(final Message message,final MessageProducingTask producingTask){
        try{
            while (this.isShouldProduce(producingTask)){
                super.wait();
            }
            this.messagesToBeConsumed.add(message);
            System.out.printf(MESSAGE_OF_MESSAGE_IS_PRODUCE, message,producingTask.getName(),
                    this.messagesToBeConsumed.size()-1);
            super.notify();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
    public synchronized Optional<Message> consume(final MessageConsumingTask consumingTask){
       try {
           while(!this.isShouldConsume(consumingTask)){
               super.wait();
           }
           final Message consumedMessage= this.messagesToBeConsumed.poll();
           System.out.printf(TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMED,consumedMessage,
                   consumingTask.getName(),this.messagesToBeConsumed.size()+1);
           super.notify();
           return Optional.ofNullable(consumedMessage);
       }catch (InterruptedException interruptedException){
           Thread.currentThread().interrupt();
          return Optional.empty();
       }
    }
    private boolean isShouldProduce(final MessageProducingTask messageProducingTask){
        return this.messagesToBeConsumed.size()<this.maxStoredMessages
                && this.messagesToBeConsumed.size()<=messageProducingTask.getMaximalAmountMessagesToProduce();
    }
    private boolean isShouldConsume(final MessageConsumingTask consumingTask){
        return !this.messagesToBeConsumed.isEmpty() &&
                this.messagesToBeConsumed.size()>=consumingTask.getMinimalAmountMessagesToConsume();
    }
}
