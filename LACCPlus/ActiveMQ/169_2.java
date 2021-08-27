//,temp,PublishOnQueueConsumedMessageInTransactionTest.java,116,135,temp,VirtualTopicDLQTest.java,377,400
//,3
public class xxx {
        @Override
        public synchronized void onMessage(Message message) {
            receivedMessageCounter++;
            latch.countDown();

            LOG.info("Consumer for destination (" + destinationName + ") latch countdown: " + latch.getCount() + " :: Number messages received "
                + this.receivedMessageCounter);

            try {
                LOG.info("Consumer for destination (" + destinationName + ") Received message id :: " + message.getJMSMessageID());

                if (!bFakeFail) {
                    LOG.info("Consumer on destination " + destinationName + " committing JMS Session for message: " + message.toString());
                    session.commit();
                } else {
                    LOG.info("Consumer on destination " + destinationName + " rolling back JMS Session for message: " + message.toString());
                    session.rollback(); // rolls back all the consumed messages
                                        // on the session to
                }

            } catch (JMSException ex) {
                LOG.error("Error reading JMS Message from destination " + destinationName + ".");
            }
        }

};