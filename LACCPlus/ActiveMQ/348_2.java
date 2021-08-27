//,temp,StompConnectTimeoutTest.java,83,119,temp,MQTTConnectTest.java,116,152
//,3
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testInactivityMonitor() throws Exception {

        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    connection = createConnection();
                    connection.getOutputStream().write(0);
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
                 return 1 == brokerService.getTransportConnectors().get(0).connectionCount();
             }
         }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(100)));

        // and it should be closed due to inactivity
        assertTrue("no dangling connections", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == brokerService.getTransportConnectors().get(0).connectionCount();
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.MILLISECONDS.toMillis(100)));

        assertTrue("no exceptions", exceptions.isEmpty());
    }

};