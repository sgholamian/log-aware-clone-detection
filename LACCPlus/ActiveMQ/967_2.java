//,temp,JMSQueueBrowserTest.java,127,154,temp,JMSInteroperabilityTest.java,410,447
//,3
public class xxx {
    @Test
    public void testOpenWireToQpidObjectMessage() throws Exception {

        // Raw Transformer doesn't expand message properties.
        assumeFalse(!transformer.equals("jms"));

        Connection openwire = createJMSConnection();
        Connection amqp = createConnection();

        openwire.start();
        amqp.start();

        Session openwireSession = openwire.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session amqpSession = amqp.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = openwireSession.createQueue(getDestinationName());

        MessageProducer openwireProducer = openwireSession.createProducer(queue);
        MessageConsumer amqpConsumer = amqpSession.createConsumer(queue);

        // Create and send the Message
        ObjectMessage outgoing = amqpSession.createObjectMessage();
        outgoing.setObject(UUID.randomUUID());
        openwireProducer.send(outgoing);

        // Now consume the ObjectMessage
        Message received = amqpConsumer.receive(2000);
        assertNotNull(received);
        LOG.info("Read new message: {}", received);
        assertTrue(received instanceof ObjectMessage);
        ObjectMessage incoming = (ObjectMessage) received;
        Object payload = incoming.getObject();
        assertNotNull(payload);
        assertTrue(payload instanceof UUID);

        amqp.close();
        openwire.close();
    }

};