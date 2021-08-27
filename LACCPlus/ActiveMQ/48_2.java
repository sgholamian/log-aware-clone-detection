//,temp,LoggingBrokerPlugin.java,538,548,temp,LoggingBrokerPlugin.java,443,449
//,3
public class xxx {
    @Override
    public void processDispatchNotification(MessageDispatchNotification messageDispatchNotification) throws Exception {
        if (isLogAll() || isLogInternalEvents() || isLogConsumerEvents()) {
            LOG.info("ProcessDispatchNotification: {}", messageDispatchNotification);
        }
        super.processDispatchNotification(messageDispatchNotification);
    }

};