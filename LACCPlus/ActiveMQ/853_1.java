//,temp,NetworkBrokerDetachTest.java,122,143,temp,PfcTimeoutTest.java,215,252
//,3
public class xxx {
    @Test
    public void testNetworkedBrokerDetach() throws Exception {
        LOG.info("Creating Consumer on the networked broker ...");
        // Create a consumer on the networked broker
        ConnectionFactory consFactory = createConnectionFactory(networkedBroker);
        Connection consConn = consFactory.createConnection();
        Session consSession = consConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        ActiveMQDestination destination = (ActiveMQDestination) consSession.createQueue(DESTINATION_NAME);
        for(int i=0; i<NUM_CONSUMERS; i++) {
            consSession.createConsumer(destination);
        }

        assertTrue("got expected consumer count from mbean within time limit",
                   verifyConsumerCount(1, destination, broker));

        LOG.info("Stopping Consumer on the networked broker ...");
        // Closing the connection will also close the consumer
        consConn.close();

        // We should have 0 consumer for the queue on the local broker
        assertTrue("got expected 0 count from mbean within time limit", verifyConsumerCount(0, destination, broker));
    }

};