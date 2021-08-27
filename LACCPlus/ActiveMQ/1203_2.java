//,temp,AMQ4887Test.java,123,163,temp,AMQ4887Test.java,64,104
//,2
public class xxx {
    public void doTestBytesMessageSetPropertyBeforeCopy(Connection connection) throws Exception {

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(name.toString());
        MessageConsumer consumer = session.createConsumer(destination);
        MessageProducer producer = session.createProducer(destination);

        BytesMessage message = session.createBytesMessage();

        for (int i=0; i < ITERATIONS; i++) {

            long sendTime = System.currentTimeMillis();
            message.setLongProperty("sendTime", sendTime);
            producer.send(message);

            LOG.debug("Receiving message " + i);
            Message receivedMessage =  consumer.receive(5000);
            assertNotNull("On message " + i, receivedMessage);
            assertTrue("On message " + i, receivedMessage instanceof BytesMessage);

            BytesMessage receivedBytesMessage = (BytesMessage) receivedMessage;

            int numElements = 0;
            try {
                while (true) {
                    receivedBytesMessage.readBoolean();
                    numElements++;
                }
            } catch (Exception ex) {
            }

            LOG.info("Iteration [{}]: Received Message contained {} boolean values.", i, numElements);
            assertEquals(i, numElements);

            long receivedSendTime = receivedBytesMessage.getLongProperty("sendTime");
            assertEquals("On message " + i, receivedSendTime, sendTime);

            // Add a new bool value on each iteration.
            message.writeBoolean(true);
        }
    }

};