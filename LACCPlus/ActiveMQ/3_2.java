//,temp,JDBCTopicMessageStore.java,330,333,temp,JDBCTopicMessageStore.java,311,319
//,3
public class xxx {
    @Override
    public void resetBatching(String clientId, String subscriptionName) {
        String key = getSubscriptionKey(clientId, subscriptionName);
        if (!pendingCompletion.contains(key))  {
            subscriberLastRecoveredMap.remove(key);
        } else {
            LOG.trace(this +  ", skip resetBatch during pending completion for: " + key);
        }
    }

};