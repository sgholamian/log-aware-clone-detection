//,temp,SmppConsumer.java,57,77,temp,SmppProducer.java,52,69
//,3
public class xxx {
    public SmppConsumer(SmppEndpoint endpoint, SmppConfiguration config, Processor processor) {
        super(endpoint, processor);

        this.configuration = config;
        this.internalSessionStateListener = new SessionStateListener() {
            @Override
            public void onStateChange(SessionState newState, SessionState oldState, Session source) {
                if (configuration.getSessionStateListener() != null) {
                    configuration.getSessionStateListener().onStateChange(newState, oldState, source);
                }

                if (newState.equals(SessionState.CLOSED)) {
                    LOG.warn("Lost connection to: {} - trying to reconnect...", getEndpoint().getConnectionString());
                    closeSession();
                    reconnect(configuration.getInitialReconnectDelay());
                }
            }
        };
        this.messageReceiverListener
                = new MessageReceiverListenerImpl(this, getEndpoint(), getProcessor(), getExceptionHandler());
    }

};