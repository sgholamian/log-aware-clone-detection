//,temp,ConnectTest.java,139,178,temp,ConnectTest.java,92,137
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testInactivityMonitor() throws Exception {

        brokerService.addConnector("stomp://0.0.0.0:0?transport.defaultHeartBeat=1000,0&transport.useKeepAlive=false");
        brokerService.start();

        Thread t1 = new Thread() {
            StompConnection connection = new StompConnection();

            @Override
            public void run() {
                try {
                    connection.open("localhost",  brokerService.getTransportConnectors().get(0).getConnectUri().getPort());
                    connection.connect("system", "manager");
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
         }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(200)));

        // and it should be closed due to inactivity
        assertTrue("no dangling connections", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                return 0 == brokerService.getTransportConnectors().get(0).connectionCount();
            }
        }, TimeUnit.SECONDS.toMillis(15), TimeUnit.MILLISECONDS.toMillis(200)));

        assertTrue("no exceptions", exceptions.isEmpty());
    }

};