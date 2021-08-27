//,temp,PooledConnectionTempQueueTest.java,100,119,temp,PooledConnectionTempQueueTest.java,75,98
//,3
public class xxx {
    public void receiveAndRespondWithMessageIdAsCorrelationId(ConnectionFactory connectionFactory,
                                                              String queueName) throws JMSException {
        Connection con = connectionFactory.createConnection();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(queueName));
        final javax.jms.Message inMessage = consumer.receive();

        String requestMessageId = inMessage.getJMSMessageID();
        LOG.debug("Received message " + requestMessageId);
        final TextMessage replyMessage = session.createTextMessage("Result");
        replyMessage.setJMSCorrelationID(inMessage.getJMSMessageID());
        final MessageProducer producer = session.createProducer(inMessage.getJMSReplyTo());
        LOG.debug("Sending reply to " + inMessage.getJMSReplyTo());
        producer.send(replyMessage);

        producer.close();
        consumer.close();
        session.close();
        con.close();
    }

};