//,temp,IgniteMessagingConsumer.java,78,85,temp,IgniteEventsConsumer.java,88,95
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        messaging.stopLocalListen(endpoint.getTopic(), predicate);

        LOG.info("Stopped Ignite Messaging consumer for topic {}.", endpoint.getTopic());
    }

};