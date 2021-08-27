//,temp,AbortSlowConsumer1Test.java,86,101,temp,AbortSlowConsumer1Test.java,68,84
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testAbortAlreadyClosedConnection() throws Exception {
        Connection conn = createConnectionFactory().createConnection();
        conn.setExceptionListener(this);

        Session sess = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        sess.createConsumer(destination);
        conn.start();
        startProducers(destination, 20);
        TimeUnit.SECONDS.sleep(1);
        LOG.info("closing connection: " + conn);
        conn.close();

        TimeUnit.SECONDS.sleep(5);
        assertTrue("no exceptions : " + exceptions.toArray(), exceptions.isEmpty());
    }

};