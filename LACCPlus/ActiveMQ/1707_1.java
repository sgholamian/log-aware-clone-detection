//,temp,CamelJmsRequestReplyNobTest.java,45,77,temp,CamelJmsTest.java,71,96
//,3
public class xxx {
    @Test
    public void testRoundTrip() throws Exception {
        Destination destination = getMandatoryBean(Destination.class, "consumeFrom");

        // lets create a message
        ConnectionFactory factoryCON = getMandatoryBean(ConnectionFactory.class, "CON");

        Connection consumerConnection = factoryCON.createConnection();
        consumerConnection.start();
        Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.info("Consuming from: " + destination);
        MessageConsumer consumer = consumerSession.createConsumer(destination);

        // lets create a message
        ConnectionFactory factoryPRO = getMandatoryBean(ConnectionFactory.class, "PRO");

        Connection producerConnection = factoryPRO.createConnection();
        producerConnection.start();
        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = producerSession.createProducer(producerSession.createQueue("incoming1"));
        Message message = producerSession.createTextMessage("Where are you");
        message.setStringProperty("foo", "bar");
        producer.send(message);

        message = consumer.receive(10000);
        assertNotNull("Should have received a message from destination: " + destination, message);

        TextMessage textMessage = assertIsInstanceOf(TextMessage.class, message);
        assertEquals("Message body", "If you don't ask me my name, I'm not going to tell you!", textMessage.getText());

    }

};