//,temp,LoggingBrokerPlugin.java,469,475,temp,LoggingBrokerPlugin.java,427,433
//,3
public class xxx {
    @Override
    public void preProcessDispatch(MessageDispatch messageDispatch) {
        if (isLogAll() || isLogInternalEvents() || isLogConsumerEvents()) {
            LOG.info("preProcessDispatch: {}", messageDispatch);
        }
        super.preProcessDispatch(messageDispatch);
    }

};