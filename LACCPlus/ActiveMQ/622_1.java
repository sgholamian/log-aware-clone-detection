//,temp,SimpleJmsQueueConnector.java,372,443,temp,SimpleJmsTopicConnector.java,371,442
//,2
public class xxx {
    @Override
    protected Destination createReplyToBridge(Destination destination, Connection replyToProducerConnection,
                                              Connection replyToConsumerConnection) {
        Queue replyToProducerQueue = (Queue)destination;
        boolean isInbound = replyToProducerConnection.equals(localConnection.get());

        if (isInbound) {
            InboundQueueBridge bridge = (InboundQueueBridge)replyToBridges.get(replyToProducerQueue);
            if (bridge == null) {
                bridge = new InboundQueueBridge() {
                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    QueueSession replyToConsumerSession = ((QueueConnection)replyToConsumerConnection)
                        .createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                    Queue replyToConsumerQueue = replyToConsumerSession.createTemporaryQueue();
                    replyToConsumerSession.close();
                    bridge.setConsumerQueue(replyToConsumerQueue);
                    bridge.setProducerQueue(replyToProducerQueue);
                    bridge.setProducerConnection((QueueConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((QueueConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(getInboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", replyToProducerQueue);
                } catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for queue: {}", replyToProducerQueue, e);
                    return null;
                }
                replyToBridges.put(replyToProducerQueue, bridge);
            }
            return bridge.getConsumerQueue();
        } else {
            OutboundQueueBridge bridge = (OutboundQueueBridge)replyToBridges.get(replyToProducerQueue);
            if (bridge == null) {
                bridge = new OutboundQueueBridge() {
                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    QueueSession replyToConsumerSession = ((QueueConnection)replyToConsumerConnection)
                        .createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                    Queue replyToConsumerQueue = replyToConsumerSession.createTemporaryQueue();
                    replyToConsumerSession.close();
                    bridge.setConsumerQueue(replyToConsumerQueue);
                    bridge.setProducerQueue(replyToProducerQueue);
                    bridge.setProducerConnection((QueueConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((QueueConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(getOutboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", replyToProducerQueue);
                } catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for queue: {}", replyToProducerQueue, e);
                    return null;
                }
                replyToBridges.put(replyToProducerQueue, bridge);
            }
            return bridge.getConsumerQueue();
        }
    }

};