//,temp,LoggingBrokerPlugin.java,538,548,temp,LoggingBrokerPlugin.java,443,449
//,3
public class xxx {
    @Override
    public void messageDelivered(ConnectionContext context, MessageReference messageReference) {
        if (isLogAll() || isLogConsumerEvents() || isLogInternalEvents()) {
            String msg = "Unable to display message.";

            msg = messageReference.getMessage().toString();

            LOG.info("Message delivered: {}", msg);
        }
        super.messageDelivered(context, messageReference);
    }

};