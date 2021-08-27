//,temp,JDBCTopicMessageStore.java,330,333,temp,JDBCTopicMessageStore.java,311,319
//,3
public class xxx {
    public void complete(String clientId, String subscriptionName) {
        pendingCompletion.remove(getSubscriptionKey(clientId, subscriptionName));
        LOG.trace(this + ", completion for: " + getSubscriptionKey(clientId, subscriptionName));
    }

};