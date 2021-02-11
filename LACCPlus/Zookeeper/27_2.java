//,temp,SaslAuthFailDesignatedClientTest.java,71,91,temp,SaslAuthMissingClientConfigTest.java,67,80
//,3
public class xxx {
    @Test
    public void testAuth() throws Exception {
        ZooKeeper zk = createClient();
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