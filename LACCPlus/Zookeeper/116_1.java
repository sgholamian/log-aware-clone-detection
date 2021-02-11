//,temp,RemoveWatchesTest.java,508,541,temp,RemoveWatchesTest.java,478,502
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveWatcherWhenNoConnection() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        MyWatcher w1 = new MyWatcher("/node1", 2);
        MyWatcher w2 = new MyWatcher("/node1", 1);
        LOG.info("Adding data watcher {} on path {}", new Object[] { w1,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w1));
        // Add multiple child watches
        LOG.info("Adding child watcher {} on path {}", new Object[] { w1,
                "/node1" });
        zk2.getChildren("/node1", w1);
        LOG.info("Adding child watcher {} on path {}", new Object[] { w1,
                "/node1" });
        zk2.getChildren("/node1", w2);
        stopServer();
        removeWatches(zk2, "/node1", w2, WatcherType.Any, true, Code.OK);
        Assert.assertTrue("Didn't remove child watcher", w2.matches());
        Assert.assertFalse("Shouldn't remove data watcher", w1.matches());
        try {
            removeWatches(zk2, "/node1", w1, WatcherType.Any, false,
                    Code.CONNECTIONLOSS);
            Assert.fail("Should throw exception as last watch removal requires server connection");
        } catch (KeeperException.ConnectionLossException nwe) {
            // expected
        }
        Assert.assertFalse("Shouldn't remove data watcher", w1.matches());

        // when local=true, here if connection not available, simply removes
        // from local session
        removeWatches(zk2, "/node1", w1, WatcherType.Any, true, Code.OK);
        Assert.assertTrue("Didn't remove data watcher", w1.matches());
    }

};