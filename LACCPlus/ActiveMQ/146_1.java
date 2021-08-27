//,temp,PooledConnectionFactoryWithTemporaryDestinationsTest.java,123,139,temp,PooledConnectionFactoryWithTemporaryDestinationsTest.java,107,121
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTemporaryQueueLeakAfterConnectionCloseWithConsumer() throws Exception {
        Connection pooledConnection = null;
        Session session = null;
        Queue tempQueue = null;
        for (int i = 0; i < 2; i++) {
            pooledConnection = pooledFactory.createConnection();
            session = pooledConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            tempQueue = session.createTemporaryQueue();
            MessageConsumer consumer = session.createConsumer(tempQueue);
            consumer.receiveNoWait();
            LOG.info("Created queue named: " + tempQueue.getQueueName());
            pooledConnection.close();
        }

        assertEquals(0, countBrokerTemporaryQueues());
    }

};