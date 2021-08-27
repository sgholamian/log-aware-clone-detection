//,temp,JmsConsumer.java,112,116,temp,SjmsConsumer.java,148,152
//,3
public class xxx {
    public void startListenerContainer() {
        LOG.trace("Starting listener container {} on destination {}", listenerContainer, getDestinationName());
        ServiceHelper.startService(listenerContainer);
        LOG.debug("Started listener container {} on destination {}", listenerContainer, getDestinationName());
    }

};