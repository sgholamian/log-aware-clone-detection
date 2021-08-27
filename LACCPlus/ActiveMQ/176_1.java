//,temp,AMQ4485Test.java,165,176,temp,AMQ5822Test.java,59,82
//,3
public class xxx {
    private Session send(int id, int messageSize, boolean transacted) throws Exception {
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(transacted, transacted ? Session.SESSION_TRANSACTED : Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.writeBytes(new byte[messageSize]);
        bytesMessage.setIntProperty("NUM", id);
        producer.send(bytesMessage);
        LOG.info("Sent:" + bytesMessage.getJMSMessageID() + " session tx: " + ((ActiveMQBytesMessage) bytesMessage).getTransactionId());
        return session;
    }

};