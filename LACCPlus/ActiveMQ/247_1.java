//,temp,FailoverTimeoutTest.java,79,106,temp,VMTransportWaitForTest.java,98,114
//,3
public class xxx {
    @Test
    public void testTimoutDoesNotFailConnectionAttempts() throws Exception {
        bs.stop();
        long timeout = 1000;

        long startTime = System.currentTimeMillis();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
            "failover:(" + tcpUri + ")" +
            "?timeout=" + timeout + "&useExponentialBackOff=false" +
            "&maxReconnectAttempts=5" + "&initialReconnectDelay=1000");
        Connection connection = cf.createConnection();
        try {
            connection.start();
            fail("Should have failed to connect");
        } catch (JMSException ex) {
            LOG.info("Caught exception on call to start: {}", ex.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        LOG.info("Time spent waiting to connect: {} ms", duration);

        assertTrue(duration > 3000);

        safeClose(connection);
    }

};