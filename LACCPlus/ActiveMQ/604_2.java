//,temp,PooledSessionExhaustionBlockTimeoutTest.java,135,159,temp,PooledSessionExhaustionTest.java,129,153
//,2
public class xxx {
            @Override
            public void run() {
                try {
                    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUri);
                    Connection connection = connectionFactory.createConnection();
                    connection.start();

                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Destination destination = session.createQueue(QUEUE);
                    MessageConsumer consumer = session.createConsumer(destination);
                    for (int i = 0; i < NUM_MESSAGES; ++i) {
                        Message msg = consumer.receive(5000);
                        if (msg == null) {
                            return;
                        }
                        numReceived++;
                        if (numReceived % 20 == 0) {
                            LOG.debug("received " + numReceived + " messages ");
                            System.runFinalization();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

};