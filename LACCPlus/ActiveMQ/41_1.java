//,temp,LoggingBrokerPlugin.java,435,441,temp,LoggingBrokerPlugin.java,393,399
//,3
public class xxx {
    @Override
    public void postProcessDispatch(MessageDispatch messageDispatch) {
        if (isLogAll() || isLogInternalEvents() || isLogConsumerEvents()) {
            LOG.info("postProcessDispatch: {}", messageDispatch);
        }
        super.postProcessDispatch(messageDispatch);
    }

};