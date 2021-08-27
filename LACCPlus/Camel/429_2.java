//,temp,PubNubProducer.java,151,155,temp,SubscriptionHelper.java,438,443
//,3
public class xxx {
            @Override
            public void onMessage(ClientSessionChannel channel, Message message) {
                LOG.debug("Received Message: {}", message);
                // convert CometD message to Camel Message
                consumer.processMessage(channel, message);
            }

};