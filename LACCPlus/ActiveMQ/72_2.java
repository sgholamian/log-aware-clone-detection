//,temp,JDBCIOExceptionHandlerTest.java,334,343,temp,JDBCIOExceptionHandlerTest.java,323,332
//,3
public class xxx {
    private void checkTransportConnectorStopped() {
        // connection is expected to fail
        try {
            factory.createConnection();
            fail("Transport connector should be stopped");
        } catch (Exception ex) {
            // expected an exception
            LOG.debug(" checkTransportConnectorStopped() threw", ex);
        }
    }

};