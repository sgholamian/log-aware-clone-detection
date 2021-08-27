//,temp,StompConnectTimeoutTest.java,83,119,temp,MQTTConnectTest.java,116,152
//,3
public class xxx {
    @Test(timeout = 15000)
    public void testInactivityMonitor() throws Exception {

        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    connection = createSocket();
                    connection.getOutputStream().write('S');
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
                 return 1 == brokerService.getTransportConnectorByScheme(getConnectorScheme()).connectionCount();
             }
        }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(250)));

        // and it should be closed due to inactivity
        assertTrue("no dangling connections", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == brokerService.getTransportConnectorByScheme(getConnectorScheme()).connectionCount();
            }
        }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(500)));

        assertTrue("no exceptions", exceptions.isEmpty());
    }

};