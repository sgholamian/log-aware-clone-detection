//,temp,TempQueue.java,66,82,temp,TempTopic.java,52,69
//,3
public class xxx {
    public void addSubscription(ConnectionContext context, Subscription sub) throws Exception {
        // Only consumers on the same connection can consume from
        // the temporary destination
        // However, we could have failed over - and we do this
        // check client side anyways ....
        if (!context.isFaultTolerant()
                && (!context.isNetworkConnection() && !tempDest
                        .getConnectionId().equals(
                                sub.getConsumerInfo().getConsumerId()
                                        .getConnectionId()))) {

            tempDest.setConnectionId(sub.getConsumerInfo().getConsumerId().getConnectionId());
            if (LOG.isDebugEnabled()) {
                LOG.debug(" changed ownership of " + this + " to "+ tempDest.getConnectionId());
            }
        }
        super.addSubscription(context, sub);
    } 

};