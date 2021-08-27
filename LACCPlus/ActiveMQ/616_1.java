//,temp,ProducerEventSource.java,83,105,temp,ConsumerEventSource.java,87,109
//,2
public class xxx {
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            ActiveMQMessage activeMessage = (ActiveMQMessage)message;
            Object command = activeMessage.getDataStructure();
            int count = 0;
            if (command instanceof ProducerInfo) {
                count = producerCount.incrementAndGet();
                count = extractProducerCountFromMessage(message, count);
                fireProducerEvent(new ProducerStartedEvent(this, destination, (ProducerInfo)command, count));
            } else if (command instanceof RemoveInfo) {
                RemoveInfo removeInfo = (RemoveInfo)command;
                if (removeInfo.isProducerRemove()) {
                    count = producerCount.decrementAndGet();
                    count = extractProducerCountFromMessage(message, count);
                    fireProducerEvent(new ProducerStoppedEvent(this, destination, (ProducerId)removeInfo.getObjectId(), count));
                }
            } else {
                LOG.warn("Unknown command: " + command);
            }
        } else {
            LOG.warn("Unknown message type: " + message + ". Message ignored");
        }
    }

};