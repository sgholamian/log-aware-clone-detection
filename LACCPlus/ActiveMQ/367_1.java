//,temp,AMQ4472Test.java,39,89,temp,AMQ4554Test.java,77,105
//,3
public class xxx {
    @Test
    public void testLostMessage() {
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.useJmx=false");
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Destination test_data_destination = session.createQueue("test"+System.currentTimeMillis());

            MessageConsumer consumer = session.createConsumer(test_data_destination);
            LOG.info("Consumer 1 connected");

            MessageProducer producer = session.createProducer(test_data_destination);
            producer.send(session.createTextMessage("Message 1"));

            // committing the session prior to the close
            session.commit();

            // starting a new transaction
            producer.send(session.createTextMessage("Message 2"));

            // in a new transaction, with prefetch>0, the message
            // 1 will be pending till second commit
            LOG.info("Closing consumer 1...");
            consumer.close();

            // create a consumer
            consumer = session.createConsumer(test_data_destination);
            LOG.info("Consumer 2 connected");

            // retrieve message previously committed to tmp queue
            Message message = consumer.receive(10000);
            if (message != null) {
                LOG.info("Got message 1:", message);
                assertEquals("expected message", "Message 1", ((TextMessage) message).getText());
                session.commit();
            } else {
                LOG.error("Expected message but it never arrived");
            }
            assertNotNull(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
            }
        }
    }

};