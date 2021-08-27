//,temp,LoggingBrokerPlugin.java,550,560,temp,LoggingBrokerPlugin.java,526,536
//,3
public class xxx {
    @Override
    public void messageDiscarded(ConnectionContext context, Subscription sub, MessageReference messageReference) {
        if (isLogAll() || isLogInternalEvents()) {
            String msg = "Unable to display message.";

            msg = messageReference.getMessage().toString();

            LOG.info("Message discarded: {}", msg);
        }
        super.messageDiscarded(context, sub, messageReference);
    }

};