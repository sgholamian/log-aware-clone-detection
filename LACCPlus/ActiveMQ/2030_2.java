//,temp,LoggingBrokerPlugin.java,550,560,temp,LoggingBrokerPlugin.java,526,536
//,3
public class xxx {
    @Override
    public void messageConsumed(ConnectionContext context, MessageReference messageReference) {
        if (isLogAll() || isLogConsumerEvents() || isLogInternalEvents()) {
            String msg = "Unable to display message.";

            msg = messageReference.getMessage().toString();

            LOG.info("Message consumed: {}", msg);
        }
        super.messageConsumed(context, messageReference);
    }

};