//,temp,LoggingBrokerPlugin.java,497,508,temp,LoggingBrokerPlugin.java,485,495
//,3
public class xxx {
    @Override
    public boolean sendToDeadLetterQueue(ConnectionContext context, MessageReference messageReference,
                                         Subscription subscription, Throwable poisonCause) {
        if (isLogAll() || isLogInternalEvents()) {
            String msg = "Unable to display message.";

            msg = messageReference.getMessage().toString();

            LOG.info("Sending to DLQ: {}", msg);
        }
        return super.sendToDeadLetterQueue(context, messageReference, subscription, poisonCause);
    }

};