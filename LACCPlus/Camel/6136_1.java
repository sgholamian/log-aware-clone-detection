//,temp,JmsJdbcXARollbackTest.java,123,134,temp,TransactedConsumeTest.java,85,101
//,3
public class xxx {
    private boolean consumedFrom(String qName) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(vmUri());
        factory.setWatchTopicAdvisories(false);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue(qName));
        Message message = consumer.receive(500);
        LOG.info("Got from queue:{} {}", qName, message);
        connection.close();
        return message != null;
    }

};