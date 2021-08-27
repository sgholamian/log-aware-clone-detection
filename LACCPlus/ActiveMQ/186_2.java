//,temp,LoggingBrokerPlugin.java,497,508,temp,LoggingBrokerPlugin.java,485,495
//,3
public class xxx {
    @Override
    public void messageExpired(ConnectionContext context, MessageReference message, Subscription subscription) {
        if (isLogAll() || isLogInternalEvents()) {
            String msg = "Unable to display message.";

            msg = message.getMessage().toString();

            LOG.info("Message has expired: {}", msg);
        }
        super.messageExpired(context, message, subscription);
    }

};