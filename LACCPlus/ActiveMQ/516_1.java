//,temp,DemandForwardingBridgeSupport.java,1959,1966,temp,ProducerBrokerExchange.java,168,175
//,2
public class xxx {
    protected long getStoredSequenceIdForMessage(MessageId messageId) {
        try {
            return brokerService.getPersistenceAdapter().getLastProducerSequenceId(messageId.getProducerId());
        } catch (IOException ignored) {
            LOG.debug("Failed to determine last producer sequence id for: {}", messageId, ignored);
        }
        return -1;
    }

};