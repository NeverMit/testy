package potoki.waitandnotify.messagetransport.broker;

import potoki.waitandnotify.messagetransport.model.Message;

import java.util.ArrayDeque;
import java.util.Queue;

public final class MessageBroker {
    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;
    public MessageBroker(final int maxStoredMessages){
        this.maxStoredMessages=maxStoredMessages;
        this.messagesToBeConsumed=new ArrayDeque<>(maxStoredMessages);
    }
    public synchronized void produce(final Message message){
        try{
            while (this.messagesToBeConsumed.size()>=this.maxStoredMessages){
                super.wait();
            }
            this.messagesToBeConsumed.add(message);
            super.notify();
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }
    public synchronized Message consume(){
       try {
           while(this.messagesToBeConsumed.isEmpty()){
               super.wait();
           }
           final Message consumedMessage= this.messagesToBeConsumed.poll();
           super.notify();
           return consumedMessage;
       }catch (InterruptedException interruptedException){
           Thread.currentThread().interrupt();
           throw new RuntimeException(interruptedException);
       }
    }
}
