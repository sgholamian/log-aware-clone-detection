//,temp,JmsJdbcXARollbackTest.java,123,134,temp,TransactedConsumeTest.java,85,101
//,3
public class xxx {
    private void sendJMSMessageToKickOffRoute() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(vmUri());
        factory.setUseAsyncSend(true);
        factory.setWatchTopicAdvisories(false);
        factory.setObjectMessageSerializationDefered(true);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(new ActiveMQQueue("scp_transacted"));
        for (int i = 0; i < messageCount; i++) {
            TextMessage message = session.createTextMessage("Some Text, messageCount:" + i);
            message.setJMSCorrelationID("pleaseCorrelate");
            producer.send(message);
        }
        LOG.info("Sent: " + messageCount);
        connection.close();
    }

};