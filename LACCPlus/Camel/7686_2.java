//,temp,SpringRabbitMQConsumer.java,127,140,temp,JmsConsumer.java,243,256
//,2
public class xxx {
    @Override
    protected void doResume() throws Exception {
        // we may not have been started before, and now the end user calls resume, so lets handle that and start it first
        if (!initialized) {
            doStart();
        } else {
            if (listenerContainer != null) {
                startListenerContainer();
            } else {
                LOG.warn(
                        "The listenerContainer is not instantiated. Probably there was a timeout during the Suspend operation. Please restart your consumer route.");
            }
        }
    }

};