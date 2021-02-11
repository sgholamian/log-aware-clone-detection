//,temp,SaslAuthFailTest.java,76,85,temp,SaslAuthMissingClientConfigTest.java,67,80
//,3
public class xxx {
    @Test
    public void testAuthFail() {
        try (ZooKeeper zk = createClient()) {
            zk.create("/path1", null, Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            Assert.fail("Should have gotten exception.");
        } catch (Exception e) {
            // ok, exception as expected.
            LOG.info("Got exception as expected: " + e);
        }
    }

};