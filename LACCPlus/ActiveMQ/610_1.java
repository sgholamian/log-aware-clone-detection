//,temp,OpenWireConnectionTimeoutTest.java,129,167,temp,AutoStompConnectTimeoutTest.java,85,123
//,2
public class xxx {
    @Test(timeout = 90000)
    public void testInactivityMonitor() throws Exception {

        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    connection = createConnection();
                    connection.getOutputStream().write('A');
                    connection.getOutputStream().flush();
                } catch (Exception ex) {
                    LOG.error("unexpected exception on connect/disconnect", ex);
                    exceptions.add(ex);
                }
            }
        };

        t1.start();

        assertTrue("one connection", Wait.waitFor(new Wait.Condition() {
             @Override
             public boolean isSatisified() throws Exception {
                 TcpTransportServer server = (TcpTransportServer) brokerService.getTransportConnectorByScheme(getConnectorScheme()).getServer();
                 return 1 == server.getCurrentTransportCount().get();
             }
        }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(250)));

        // and it should be closed due to inactivity
        assertTrue("no dangling connections", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                TcpTransportServer server = (TcpTransportServer) brokerService.getTransportConnectorByScheme(getConnectorScheme()).getServer();
                return 0 == server.getCurrentTransportCount().get();
            }
        }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(500)));

        assertTrue("no exceptions", exceptions.isEmpty());
    }

};