//,temp,MQTTVirtualTopicSubscriptionStrategy.java,226,245,temp,AmqpConnection.java,777,793
//,3
public class xxx {
    private void deleteDurableQueues(List<ActiveMQQueue> queues) {
        try {
            for (ActiveMQQueue queue : queues) {
                LOG.debug("Removing queue subscription for {} ",queue.getPhysicalName());
                DestinationInfo removeAction = new DestinationInfo();
                removeAction.setConnectionId(protocol.getConnectionId());
                removeAction.setDestination(queue);
                removeAction.setOperationType(DestinationInfo.REMOVE_OPERATION_TYPE);

                protocol.sendToActiveMQ(removeAction, new ResponseHandler() {
                    @Override
                    public void onResponse(MQTTProtocolConverter converter, Response response) throws IOException {
                        // ignore failures..
                    }
                });
            }
        } catch (Throwable e) {
            LOG.warn("Could not delete the MQTT queue subscriptions.", e);
        }
    }

};