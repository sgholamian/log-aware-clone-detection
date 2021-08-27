//,temp,AMQ1893Test.java,129,192,temp,AMQ4092Test.java,156,225
//,3
public class xxx {
        public void consume() throws Exception {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    brokerService.getTransportConnectors().get(0).getConnectUri().toString()
            );

            connectionFactory.setExceptionListener(exceptionListener);
            final int totalMessageCount = NUM_TO_SEND_PER_PRODUCER * DESTINATIONS.length * NUM_PRODUCERS;
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

                    boolean first = false;
                    try {
                        first = message.getBooleanProperty("JMSXGroupFirstForConsumer");
                    } catch (JMSException e) {
                        e.printStackTrace();
                        exceptions.put(Thread.currentThread(), e);
                    }
                    assertTrue("Always is first message", first);
                    if (counter.incrementAndGet() == totalMessageCount) {
                            log.info("Got all:" + counter.get());
                            finishLatch.countDown();

                    }
                }
            };

            int consumerCount = DESTINATIONS.length * 100;
            Connection[] connections = new Connection[consumerCount];

            Session[] sessions = new Session[consumerCount];
            MessageConsumer[] consumers = new MessageConsumer[consumerCount];

            for (int i = 0; i < consumerCount; i++) {
                connections[i] = connectionFactory.createConnection();
                connections[i].start();

                sessions[i] = connections[i].createSession(false, Session.AUTO_ACKNOWLEDGE);

                consumers[i] = sessions[i].createConsumer(DESTINATIONS[i%DESTINATIONS.length], null);
                consumers[i].setMessageListener(listener);
            }


            log.info("received " + counter.get() + " messages");

            assertTrue("got all messages in time", finishLatch.await(4, TimeUnit.MINUTES));

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