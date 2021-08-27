//,temp,ProducerEventSource.java,83,105,temp,ConsumerEventSource.java,87,109
//,2
public class xxx {
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            ActiveMQMessage activeMessage = (ActiveMQMessage)message;
            Object command = activeMessage.getDataStructure();
            int count = 0;
            if (command instanceof ConsumerInfo) {
                count = consumerCount.incrementAndGet();
                count = extractConsumerCountFromMessage(message, count);
                fireConsumerEvent(new ConsumerStartedEvent(this, destination, (ConsumerInfo)command, count));
            } else if (command instanceof RemoveInfo) {
                RemoveInfo removeInfo = (RemoveInfo)command;
                if (removeInfo.isConsumerRemove()) {
                    count = consumerCount.decrementAndGet();
                    count = extractConsumerCountFromMessage(message, count);
                    fireConsumerEvent(new ConsumerStoppedEvent(this, destination, (ConsumerId)removeInfo.getObjectId(), count));
                }
            } else {
                LOG.warn("Unknown command: " + command);
            }
        } else {
            LOG.warn("Unknown message type: " + message + ". Message ignored");
        }
    }

};