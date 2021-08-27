//,temp,AMQ1893Test.java,129,192,temp,AMQ4092Test.java,156,225
//,3
public class xxx {
        public void consume() throws Exception {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    brokerService.getTransportConnectors().get(0).getConnectUri().toString()
            );


            final int totalMessageCount = MESSAGE_COUNT_OF_ONE_GROUP * PRIORITIES.length;
            final AtomicInteger counter = new AtomicInteger();
            final MessageListener listener = new MessageListener() {
                public void onMessage(Message message) {

                    if (debug) {
                        try {
                            log.info(((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }

                    if (counter.incrementAndGet() == totalMessageCount) {

                            finishLatch.countDown();

                    }
                }
            };

            int consumerCount = PRIORITIES.length;
            Connection[] connections = new Connection[consumerCount];
            Session[] sessions = new Session[consumerCount];
            MessageConsumer[] consumers = new MessageConsumer[consumerCount];

            for (int i = 0; i < consumerCount; i++) {
                String selector = "priority = " + PRIORITIES[i];

                connections[i] = connectionFactory.createConnection();
                sessions[i] = connections[i].createSession(false, Session.AUTO_ACKNOWLEDGE);

                consumers[i] = sessions[i].createConsumer(destination, selector);
                consumers[i].setMessageListener(listener);
            }

            for (Connection connection : connections) {
                connection.start();
            }

            log.info("received " + counter.get() + " messages");

            assertTrue("got all messages in time", finishLatch.await(60, TimeUnit.SECONDS));

            log.info("received " + counter.get() + " messages");

            for (MessageConsumer consumer : consumers) {
                consumer.close();
            }

            for (Session session : sessions) {
                session.close();
            }

            for (Connection connection : connections) {
                connection.close();
            }
        }

};