//,temp,ZooKeeperServerStartupTest.java,115,162,temp,ZooKeeperServerStartupTest.java,77,109
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testClientConnectionRequestDuringStartupWithNettyServerCnxn()
            throws Exception {
        tmpDir = ClientBase.createTmpDir();
        ClientBase.setupTestEnv();

        String originalServerCnxnFactory = System
                .getProperty(ServerCnxnFactory.ZOOKEEPER_SERVER_CNXN_FACTORY);
        try {
            System.setProperty(ServerCnxnFactory.ZOOKEEPER_SERVER_CNXN_FACTORY,
                    NettyServerCnxnFactory.class.getName());
            startSimpleZKServer(startupDelayLatch);
            SimpleZooKeeperServer simplezks = (SimpleZooKeeperServer) zks;
            Assert.assertTrue(
                    "Failed to invoke zks#startup() method during server startup",
                    simplezks.waitForStartupInvocation(10));

            CountdownWatcher watcher = new CountdownWatcher();
            ZooKeeper zkClient = new ZooKeeper(HOSTPORT,
                    ClientBase.CONNECTION_TIMEOUT, watcher);

            Assert.assertFalse(
                    "Since server is not fully started, zks#createSession() shouldn't be invoked",
                    simplezks.waitForSessionCreation(5));

            LOG.info(
                    "Decrements the count of the latch, so that server will proceed with startup");
            startupDelayLatch.countDown();

            Assert.assertTrue("waiting for server being up ", ClientBase
                    .waitForServerUp(HOSTPORT, ClientBase.CONNECTION_TIMEOUT));

            Assert.assertTrue(
                    "Failed to invoke zks#createSession() method during client session creation",
                    simplezks.waitForSessionCreation(5));
            watcher.waitForConnected(ClientBase.CONNECTION_TIMEOUT);
            zkClient.close();
        } finally {
            // reset cnxn factory
            if (originalServerCnxnFactory == null) {
                System.clearProperty(
                        ServerCnxnFactory.ZOOKEEPER_SERVER_CNXN_FACTORY);
                return;
            }
            System.setProperty(ServerCnxnFactory.ZOOKEEPER_SERVER_CNXN_FACTORY,
                    originalServerCnxnFactory);
        }
    }

};