//,temp,PooledConnectionFactoryWithTemporaryDestinationsTest.java,123,139,temp,PooledConnectionFactoryWithTemporaryDestinationsTest.java,107,121
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTemporaryTopicLeakAfterConnectionClose() throws Exception {
        Connection pooledConnection = null;
        Session session = null;
        Topic tempTopic = null;
        for (int i = 0; i < 2; i++) {
            pooledConnection = pooledFactory.createConnection();
            session = pooledConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            tempTopic = session.createTemporaryTopic();
            LOG.info("Created topic named: " + tempTopic.getTopicName());
            pooledConnection.close();
        }

        assertEquals(0, countBrokerTemporaryTopics());
    }

};