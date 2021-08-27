//,temp,ConnectTest.java,139,178,temp,ConnectTest.java,92,137
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testJaasDualStopWithOpenConnection() throws Exception {

        brokerService.setPlugins(new BrokerPlugin[]{new JaasDualAuthenticationPlugin()});
        brokerService.addConnector("stomp://0.0.0.0:0?transport.closeAsync=false");
        brokerService.start();

        final CountDownLatch doneConnect = new CountDownLatch(1);
        final int listenPort = brokerService.getTransportConnectors().get(0).getConnectUri().getPort();
        Thread t1 = new Thread() {
            StompConnection connection = new StompConnection();

            @Override
            public void run() {
                try {
                    connection.open("localhost", listenPort);
                    connection.connect("system", "manager");
                    doneConnect.countDown();
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

        assertTrue("connected on time", doneConnect.await(5, TimeUnit.SECONDS));
        brokerService.stop();

        // server socket should be available after stop
        ServerSocket socket = ServerSocketFactory.getDefault().createServerSocket();
        socket.setReuseAddress(true);
        InetAddress address = InetAddress.getLocalHost();
        socket.bind(new InetSocketAddress(address, listenPort));
        LOG.info("bound address: " + socket);
        socket.close();
        assertTrue("no exceptions", exceptions.isEmpty());
    }

};