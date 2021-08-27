//,temp,JmsProducerClient.java,164,251,temp,JmsProducerClient.java,78,162
//,3
public class xxx {
    public void sendCountBasedMessages(long messageCount) throws JMSException {
        // Parse through different ways to send messages
        // Avoided putting the condition inside the loop to prevent effect on performance
        Destination[] dest = createDestinations(destCount);

        // Create a producer, if none is created.
        if (getJmsProducer() == null) {
            if (dest.length == 1) {
                createJmsProducer(dest[0]);
            } else {
                createJmsProducer();
            }
        }
        try {
            getConnection().start();
            if (client.getMsgFileName() != null) {
                LOG.info("Starting to publish " +
                    messageCount +
                    " messages from file " +
                    client.getMsgFileName()
                );
            } else {
                LOG.info("Starting to publish " +
                    messageCount +
                    " messages of size " +
                    client.getMessageSize() +
                    " byte(s)."
                );
            }

            // Send one type of message only, avoiding the creation of different messages on sending
            if (!client.isCreateNewMsg()) {
                // Create only a single message
                createJmsTextMessage();

                // Send to more than one actual destination
                if (dest.length > 1) {
                    for (int i = 0; i < messageCount; i++) {
                        for (int j = 0; j < dest.length; j++) {
                            getJmsProducer().send(dest[j], getJmsTextMessage());
                            incThroughput();
                            sleep();
                            commitTxIfNecessary();
                        }
                    }
                    // Send to only one actual destination
                } else {
                    for (int i = 0; i < messageCount; i++) {
                        getJmsProducer().send(getJmsTextMessage());
                        incThroughput();
                        sleep();
                        commitTxIfNecessary();
                    }
                }

                // Send different type of messages using indexing to identify each one.
                // Message size will vary. Definitely slower, since messages properties
                // will be set individually each send.
            } else {
                // Send to more than one actual destination
                if (dest.length > 1) {
                    for (int i = 0; i < messageCount; i++) {
                        for (int j = 0; j < dest.length; j++) {
                            getJmsProducer().send(dest[j], createJmsTextMessage("Text Message [" + i + "]"));
                            incThroughput();
                            sleep();
                            commitTxIfNecessary();
                        }
                    }

                    // Send to only one actual destination
                } else {
                    for (int i = 0; i < messageCount; i++) {
                        getJmsProducer().send(createJmsTextMessage("Text Message [" + i + "]"));
                        incThroughput();
                        sleep();
                        commitTxIfNecessary();
                    }
                }
            }
        } finally {
            LOG.info("Finished sending");
            getConnection().close();
        }
    }

};