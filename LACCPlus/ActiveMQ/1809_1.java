//,temp,JmsProducerClient.java,164,251,temp,JmsProducerClient.java,78,162
//,3
public class xxx {
    public void sendTimeBasedMessages(long duration) throws JMSException {
        long endTime = System.currentTimeMillis() + duration;
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
                LOG.info("Starting to publish messages from file " +
                        client.getMsgFileName() +
                        " for " +
                        duration +
                        " ms");
            } else {
                LOG.info("Starting to publish " +
                        client.getMessageSize() +
                        " byte(s) messages for " +
                        duration +
                        " ms");
            }
            // Send one type of message only, avoiding the creation of different messages on sending
            if (!client.isCreateNewMsg()) {
                // Create only a single message
                createJmsTextMessage();

                // Send to more than one actual destination
                if (dest.length > 1) {
                    while (System.currentTimeMillis() - endTime < 0) {
                        for (int j = 0; j < dest.length; j++) {
                            getJmsProducer().send(dest[j], getJmsTextMessage());
                            incThroughput();
                            sleep();
                            commitTxIfNecessary();
                        }
                    }
                    // Send to only one actual destination
                } else {
                    while (System.currentTimeMillis() - endTime < 0) {
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
                long count = 1;
                if (dest.length > 1) {
                    while (System.currentTimeMillis() - endTime < 0) {
                        for (int j = 0; j < dest.length; j++) {
                            getJmsProducer().send(dest[j], createJmsTextMessage("Text Message [" + count++ + "]"));
                            incThroughput();
                            sleep();
                            commitTxIfNecessary();
                        }
                    }

                    // Send to only one actual destination
                } else {
                    while (System.currentTimeMillis() - endTime < 0) {

                        getJmsProducer().send(createJmsTextMessage("Text Message [" + count++ + "]"));
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