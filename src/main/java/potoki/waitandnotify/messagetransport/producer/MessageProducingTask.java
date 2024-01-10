package potoki.waitandnotify.messagetransport.producer;

import potoki.waitandnotify.messagetransport.broker.MessageBroker;
import potoki.waitandnotify.messagetransport.model.Message;

import java.util.concurrent.TimeUnit;

public final class MessageProducingTask implements Runnable {
    private static final String MESSAGE_OF_MESSAGE_IS_PRODUCE="Message '%s' is produced.\n";
    private static final int SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING= 3;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;

    public MessageProducingTask(final MessageBroker messageBroker){
        this.messageBroker=messageBroker;
        this.messageFactory=new MessageFactory();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                final Message producedMessage = this.messageFactory.create();
                TimeUnit.SECONDS.sleep(SECONDS_DURATION_TO_SLEEP_BEFORE_PRODUCING);
                this.messageBroker.produce(producedMessage);
                System.out.printf(MESSAGE_OF_MESSAGE_IS_PRODUCE, producedMessage);
            }
        }catch (final InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        }
    }

    private static final class MessageFactory{
        private static final int INITIAL_NEXT_MESSAGE_INDEX=1;
        private static final String TEMPLATE_CREATED_MESSAGE_DATA="Message#%d";
        private int nextMessageIndex;

        public MessageFactory(){
            this.nextMessageIndex=INITIAL_NEXT_MESSAGE_INDEX;
        }
        public Message create(){
            return new Message(String.format(TEMPLATE_CREATED_MESSAGE_DATA,this.nextMessageIndex++));
        }
    }
}
