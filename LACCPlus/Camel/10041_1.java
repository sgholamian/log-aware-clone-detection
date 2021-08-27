//,temp,GooglePubsubConsumer.java,143,182,temp,CamelMessageReceiver.java,50,75
//,3
public class xxx {
        private void synchronousPull(String subscriptionName) {
            while (isRunAllowed() && !isSuspendingOrSuspended()) {
                try (SubscriberStub subscriber = endpoint.getComponent().getSubscriberStub(endpoint.getServiceAccountKey())) {

                    PullRequest pullRequest = PullRequest.newBuilder()
                            .setMaxMessages(endpoint.getMaxMessagesPerPoll())
                            .setReturnImmediately(false)
                            .setSubscription(subscriptionName)
                            .build();

                    PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
                    for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
                        PubsubMessage pubsubMessage = message.getMessage();
                        Exchange exchange = createExchange(true);
                        exchange.getIn().setBody(pubsubMessage.getData().toByteArray());

                        exchange.getIn().setHeader(GooglePubsubConstants.ACK_ID, message.getAckId());
                        exchange.getIn().setHeader(GooglePubsubConstants.MESSAGE_ID, pubsubMessage.getMessageId());
                        exchange.getIn().setHeader(GooglePubsubConstants.PUBLISH_TIME, pubsubMessage.getPublishTime());

                        if (null != pubsubMessage.getAttributesMap()) {
                            exchange.getIn().setHeader(GooglePubsubConstants.ATTRIBUTES, pubsubMessage.getAttributesMap());
                        }

                        if (endpoint.getAckMode() != GooglePubsubConstants.AckMode.NONE) {
                            exchange.adapt(ExtendedExchange.class)
                                    .addOnCompletion(new AcknowledgeSync(subscriber, subscriptionName));
                        }

                        try {
                            processor.process(exchange);
                        } catch (Exception e) {
                            getExceptionHandler().handleException(e);
                        }
                    }
                } catch (IOException e) {
                    localLog.error("Failure getting messages from PubSub", e);
                }
            }
        }

};