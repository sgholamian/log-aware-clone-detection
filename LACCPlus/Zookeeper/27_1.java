//,temp,SaslAuthFailDesignatedClientTest.java,71,91,temp,SaslAuthMissingClientConfigTest.java,67,80
//,3
public class xxx {
    @Test
    public void testAuth() throws Exception {
        // Cannot use createClient here because server may close session before 
        // JMXEnv.ensureAll is called which will fail the test case
        CountdownWatcher watcher = new CountdownWatcher();
        TestableZooKeeper zk = new TestableZooKeeper(hostPort, CONNECTION_TIMEOUT, watcher);
        if (!watcher.clientConnected.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS))
        {
            Assert.fail("Unable to connect to server");
        }
        try {
            zk.create("/path1", null, Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            Assert.fail("Should have gotten exception.");
        } catch (KeeperException e) {
            // ok, exception as expected.
            LOG.info("Got exception as expected: " + e);
        }
        finally {
            zk.close();
        }
    }

};