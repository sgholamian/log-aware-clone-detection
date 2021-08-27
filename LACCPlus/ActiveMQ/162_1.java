//,temp,ConcurrentDestinationCreationTest.java,106,123,temp,ConcurrentDestinationCreationTest.java,81,99
//,3
public class xxx {
                    @Override
                    public void run() {
                        try {
                            final Connection connection = factory.createConnection();
                            connection.start();
                            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                            for (int j = 0; j < jobs; j++) {
                                final MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("Q.>"));
                                consumer.receiveNoWait();
                            }
                            connection.close();
                            allDone.countDown();
                            LOG.info("Consumers done!");
                        } catch (Exception ignored) {
                            LOG.error("unexpected ", ignored);
                            exceptions.add(ignored);
                        }
                    }

};