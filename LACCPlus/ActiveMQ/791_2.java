//,temp,PooledConnectionSecurityExceptionTest.java,157,181,temp,PooledConnectionSecurityExceptionTest.java,119,155
//,3
public class xxx {
    @Test
    public void testFailureGetsNewConnectionOnRetry() throws Exception {
        pooledConnFact.setMaxConnections(1);

        final PooledConnection connection1 = (PooledConnection) pooledConnFact.createConnection("invalid", "credentials");

        try {
            connection1.start();
            fail("Should fail to connect");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        }

        // The pool should process the async error
        assertTrue("Should get new connection", Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return connection1.getConnection() !=
                    ((PooledConnection) pooledConnFact.createConnection("invalid", "credentials")).getConnection();
            }
        }));

        final PooledConnection connection2 = (PooledConnection) pooledConnFact.createConnection("invalid", "credentials");
        assertNotSame(connection1.getConnection(), connection2.getConnection());

        try {
            connection2.start();
            fail("Should fail to connect");
        } catch (JMSSecurityException ex) {
            LOG.info("Caught expected security error");
        } finally {
            connection2.close();
        }

        connection1.close();
    }

};