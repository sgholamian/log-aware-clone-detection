//,temp,PooledConnectionSecurityExceptionTest.java,183,208,temp,PooledConnectionSecurityExceptionTest.java,67,84
//,3
public class xxx {
    @Test
    public void testFailedConnectThenSucceeds() throws JMSException {
        Connection connection = pooledConnFact.createConnection("invalid", "credentials");

        try {
            connection.start();
            fail("Should fail to connect");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        }

        connection = pooledConnFact.createConnection("system", "manager");
        connection.start();

        LOG.info("Successfully create new connection.");

        connection.close();
    }

};