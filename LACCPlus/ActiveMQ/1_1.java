//,temp,AbortSlowConsumerStrategyView.java,91,99,temp,AbortSlowConsumerStrategyView.java,80,89
//,3
public class xxx {
    public void abortConnection(ObjectName consumerToAbort) {
        Subscription sub = broker.getSubscriber(consumerToAbort);
        if (sub != null) {
            LOG.info("aborting consumer connection via jmx: {}", sub.getConsumerInfo().getConsumerId().getConnectionId());
            strategy.abortConsumer(sub, true);
        } else {
            LOG.warn("cannot resolve subscription matching name: {}", consumerToAbort);
        }
    }

};