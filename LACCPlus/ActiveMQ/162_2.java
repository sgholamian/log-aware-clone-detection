//,temp,ConcurrentDestinationCreationTest.java,106,123,temp,ConcurrentDestinationCreationTest.java,81,99
//,3
public class xxx {
                    @Override
                    public void run() {
                        try {
                            final Connection connection = factory.createConnection();
                            connection.start();
                            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                            for (int j = 0; j< jobs*10; j++) {
                                final MessageProducer producer = session.createProducer(new ActiveMQQueue("Q." + (j%destinationCount)));
                                producer.send(session.createMessage());
                            }
                            connection.close();
                            allDone.countDown();
                            LOG.info("Producers done!");
                        } catch (Exception ignored) {
                            LOG.error("unexpected ", ignored);
                            exceptions.add(ignored);
                        }
                    }

};