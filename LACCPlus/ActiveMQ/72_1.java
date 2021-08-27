//,temp,JDBCIOExceptionHandlerTest.java,334,343,temp,JDBCIOExceptionHandlerTest.java,323,332
//,3
public class xxx {
    private void checkTransportConnectorStarted() {
        // connection is expected to succeed
        try {
            Connection conn = factory.createConnection();
            conn.close();
        } catch (Exception ex) {
            LOG.debug("checkTransportConnectorStarted() threw", ex);
            fail("Transport connector should have been started");
        }
    }

};