//,temp,AbortSlowConsumerStrategyView.java,91,99,temp,AbortSlowConsumerStrategyView.java,80,89
//,3
public class xxx {
    public void abortConsumer(ObjectName consumerToAbort) {
        Subscription sub = broker.getSubscriber(consumerToAbort);
        if (sub != null) {
            LOG.info("aborting consumer via jmx: {}", sub.getConsumerInfo().getConsumerId());
            strategy.abortConsumer(sub, false);
        } else {
            LOG.warn("cannot resolve subscription matching name: {}", consumerToAbort);
        }

    }

};