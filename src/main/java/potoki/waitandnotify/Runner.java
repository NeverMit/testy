package potoki.waitandnotify;

import potoki.waitandnotify.messagetransport.broker.MessageBroker;
import potoki.waitandnotify.messagetransport.consumer.MessageConsumingTask;
import potoki.waitandnotify.messagetransport.producer.MessageFactory;
import potoki.waitandnotify.messagetransport.producer.MessageProducingTask;

import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        final int brokerMaxStoredMessages=15;
        final MessageBroker messageBroker=new MessageBroker(brokerMaxStoredMessages);

        final MessageFactory messageFactory=new MessageFactory();

        final Thread firstProducingThread=new Thread(new MessageProducingTask(messageBroker,messageFactory,
                brokerMaxStoredMessages,"PRODUCER-1"));
        final Thread secondProducingThread=new Thread(new MessageProducingTask(messageBroker,messageFactory,
                10,"PRODUCER-2"));
        final Thread thirdProducingThread=new Thread(new MessageProducingTask(messageBroker,messageFactory,
                5,"PRODUCER-3"));

        final Thread firstConsumingThread=new Thread(new MessageConsumingTask(messageBroker,
                0, "CONSUMER-1"));
        final Thread secondConsumingThread=new Thread(new MessageConsumingTask(messageBroker,
                6,"CONSUMER-2"));
        final Thread thirdConsumingThread=new Thread(new MessageConsumingTask(messageBroker,
                11,"CONSUMER-3"));
        startThreads(firstProducingThread,
                secondProducingThread,
                thirdProducingThread,
                firstConsumingThread,
                secondConsumingThread,
                thirdConsumingThread
        );
    }
    private static void startThreads(final Thread...threads){
        Arrays.stream(threads).forEach(Thread::start);
    }
}
