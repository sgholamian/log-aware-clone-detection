//,temp,JmsJdbcXARollbackTest.java,114,125,temp,MemoryUsageCleanupTest.java,208,233
//,3
public class xxx {
    private boolean consumedFrom(String qName) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://testXA");
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