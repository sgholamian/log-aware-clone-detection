//,temp,PulsarConsumerReadCompactedIT.java,74,81,temp,GroupedExchangeRoundtripIT.java,61,69
//,3
public class xxx {
    @Override
    public void createTopicSubscription() {
        try {
            createTopicSubscriptionPair(TOPIC_NAME, SUBSCRIPTION_NAME);
        } catch (Exception e) {
            // May be ignored because it could have been created.
            LOG.warn("Failed to create the subscription pair {}", e.getMessage());
        }
    }

};