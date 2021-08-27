//,temp,TotalMessageCountTest.java,123,141,temp,AMQ5921Test.java,49,64
//,3
public class xxx {
    @Test
    public void testVoidSupport() throws Exception {
        sendMessage();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(name.getMethodName());

        MessageConsumer consumer = session.createConsumer(destination);
        ActiveMQObjectMessage msg = (ActiveMQObjectMessage) consumer.receive();
        AMQ5921MessagePayload payload = (AMQ5921MessagePayload) msg.getObject();
        LOG.info("Received: {}", payload.getField1());

        session.close();
    }

};