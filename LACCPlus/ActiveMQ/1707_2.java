//,temp,CamelJmsRequestReplyNobTest.java,45,77,temp,CamelJmsTest.java,71,96
//,3
public class xxx {
    @Test
    public void testConsumingViaJMSReceivesMessageFromCamel() throws Exception {
        // lets create a message
        Destination destination = getMandatoryBean(Destination.class, "consumeFrom");
        ConnectionFactory factory = getMandatoryBean(ConnectionFactory.class, "connectionFactory");
        ProducerTemplate template = getMandatoryBean(ProducerTemplate.class, "camelTemplate");
        assertNotNull("template is valid", template);
        
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        LOG.info("Consuming from: " + destination);
        MessageConsumer consumer = session.createConsumer(destination);

        // now lets send a message
        template.sendBody("seda:consumer", expectedBody);

        Message message = consumer.receive(5000);
        assertNotNull("Should have received a message from destination: " + destination, message);

        TextMessage textMessage = assertIsInstanceOf(TextMessage.class, message);
        assertEquals("Message body", expectedBody, textMessage.getText());

        LOG.info("Received message: " + message);
    }

};