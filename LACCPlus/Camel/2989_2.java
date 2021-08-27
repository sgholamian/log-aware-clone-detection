//,temp,IgniteMessagingConsumer.java,78,85,temp,IgniteEventsConsumer.java,88,95
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        events.stopLocalListen(predicate, eventTypes);

        LOG.info("Stopped local Ignite Events consumer for events: {}.", Arrays.asList(eventTypes));
    }

};