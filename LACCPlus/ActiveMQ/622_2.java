//,temp,SimpleJmsQueueConnector.java,372,443,temp,SimpleJmsTopicConnector.java,371,442
//,2
public class xxx {
    @Override
    protected Destination createReplyToBridge(Destination destination, Connection replyToProducerConnection,
                                              Connection replyToConsumerConnection) {
        Topic replyToProducerTopic = (Topic)destination;
        boolean isInbound = replyToProducerConnection.equals(localConnection.get());

        if (isInbound) {
            InboundTopicBridge bridge = (InboundTopicBridge)replyToBridges.get(replyToProducerTopic);
            if (bridge == null) {
                bridge = new InboundTopicBridge() {
                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    TopicSession replyToConsumerSession = ((TopicConnection)replyToConsumerConnection)
                        .createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                    Topic replyToConsumerTopic = replyToConsumerSession.createTemporaryTopic();
                    replyToConsumerSession.close();
                    bridge.setConsumerTopic(replyToConsumerTopic);
                    bridge.setProducerTopic(replyToProducerTopic);
                    bridge.setProducerConnection((TopicConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((TopicConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(getInboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", replyToProducerTopic);
                } catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for topic: {}", replyToProducerTopic, e);
                    return null;
                }
                replyToBridges.put(replyToProducerTopic, bridge);
            }
            return bridge.getConsumerTopic();
        } else {
            OutboundTopicBridge bridge = (OutboundTopicBridge)replyToBridges.get(replyToProducerTopic);
            if (bridge == null) {
                bridge = new OutboundTopicBridge() {
                    @Override
                    protected Destination processReplyToDestination(Destination destination) {
                        return null;
                    }
                };
                try {
                    TopicSession replyToConsumerSession = ((TopicConnection)replyToConsumerConnection)
                        .createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                    Topic replyToConsumerTopic = replyToConsumerSession.createTemporaryTopic();
                    replyToConsumerSession.close();
                    bridge.setConsumerTopic(replyToConsumerTopic);
                    bridge.setProducerTopic(replyToProducerTopic);
                    bridge.setProducerConnection((TopicConnection)replyToProducerConnection);
                    bridge.setConsumerConnection((TopicConnection)replyToConsumerConnection);
                    bridge.setDoHandleReplyTo(false);
                    if (bridge.getJmsMessageConvertor() == null) {
                        bridge.setJmsMessageConvertor(getOutboundMessageConvertor());
                    }
                    bridge.setJmsConnector(this);
                    bridge.start();
                    LOG.info("Created replyTo bridge for {}", replyToProducerTopic);
                } catch (Exception e) {
                    LOG.error("Failed to create replyTo bridge for topic: {}", replyToProducerTopic, e);
                    return null;
                }
                replyToBridges.put(replyToProducerTopic, bridge);
            }
            return bridge.getConsumerTopic();
        }
    }

};