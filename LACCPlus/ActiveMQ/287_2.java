//,temp,JMSInteroperabilityTest.java,449,488,temp,JMSInteroperabilityTest.java,371,408
//,3
public class xxx {
    @Test
    public void testQpidToOpenWireObjectMessage() throws Exception {

        // Raw Transformer doesn't expand message properties.
        assumeFalse(!transformer.equals("jms"));

        Connection openwire = createJMSConnection();
        Connection amqp = createConnection();

        openwire.start();
        amqp.start();

        Session openwireSession = openwire.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session amqpSession = amqp.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = openwireSession.createQueue(getDestinationName());

        MessageProducer amqpProducer = amqpSession.createProducer(queue);
        MessageConsumer openwireConsumer = openwireSession.createConsumer(queue);

        // Create and send the Message
        ObjectMessage outgoing = amqpSession.createObjectMessage();
        outgoing.setObject(UUID.randomUUID());
        amqpProducer.send(outgoing);

        // Now consume the ObjectMessage
        Message received = openwireConsumer.receive(2000);
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