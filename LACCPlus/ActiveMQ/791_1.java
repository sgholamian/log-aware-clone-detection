//,temp,PooledConnectionSecurityExceptionTest.java,157,181,temp,PooledConnectionSecurityExceptionTest.java,119,155
//,3
public class xxx {
    @Test
    public void testFailureGetsNewConnectionOnRetryBigPool() throws JMSException {
        pooledConnFact.setMaxConnections(10);

        Connection connection1 = pooledConnFact.createConnection("invalid", "credentials");
        try {
            connection1.start();
            fail("Should fail to connect");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        }

        Connection connection2 = pooledConnFact.createConnection("invalid", "credentials");
        try {
            connection2.start();
            fail("Should fail to connect");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        }

        assertNotSame(connection1, connection2);

        connection1.close();
        connection2.close();
    }

};