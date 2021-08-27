//,temp,VirtualTopicDLQTest.java,319,365,temp,AMQ1853Test.java,262,311
//,3
public class xxx {
        @Override
        public void run() {

            try {
                LOG.info("Started TestConsumer for destination (" + destinationName + ")");

                connectionFactory = new ActiveMQConnectionFactory(jmsConnectionURI);
                connection = (ActiveMQConnection) connectionFactory.createConnection();
                connection.start();
                session = connection.createSession(true, Session.SESSION_TRANSACTED);

                RedeliveryPolicy policy = connection.getRedeliveryPolicy();
                policy.setInitialRedeliveryDelay(1);
                policy.setUseExponentialBackOff(false);
                policy.setMaximumRedeliveries(maxRedeliveries);

                connection.setExceptionListener(this);

                Destination destination = null;
                if (isTopic) {
                    destination = session.createTopic(destinationName);
                } else {
                    destination = session.createQueue(destinationName);
                }

                consumer = session.createConsumer(destination);
                consumer.setMessageListener(this);

                while (!bStop) {
                    Thread.sleep(100);
                }

                LOG.info("Finished TestConsumer for destination name (" + destinationName + ") remaining " + this.latch.getCount() + " messages "
                    + this.toString());

            } catch (Exception e) {
                LOG.error("Consumer (" + destinationName + ") Caught: " + e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    LOG.error("Closing connection/session (" + destinationName + ")Caught: " + e);
                }
            }
        }

};