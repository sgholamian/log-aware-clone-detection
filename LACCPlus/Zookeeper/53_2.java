//,temp,RemoveWatchesTest.java,1039,1109,temp,RemoveWatchesTest.java,917,972
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveAllDataWatchesOnAPath() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        final CountDownLatch dWatchCount = new CountDownLatch(2);
        final CountDownLatch rmWatchCount = new CountDownLatch(2);
        Watcher w1 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                switch (event.getType()) {
                case DataWatchRemoved:
                    rmWatchCount.countDown();
                    break;
                case NodeDataChanged:
                    dWatchCount.countDown();
                    break;
                default:
                    break;
                }
            }
        };
        Watcher w2 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                switch (event.getType()) {
                case DataWatchRemoved:
                    rmWatchCount.countDown();
                    break;
                case NodeDataChanged:
                    dWatchCount.countDown();
                    break;
                default:
                    break;
                }
            }
        };
        // Add multiple data watches
        LOG.info("Adding data watcher {} on path {}", new Object[] { w1,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w1));
        LOG.info("Adding data watcher {} on path {}", new Object[] { w2,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w2));

        Assert.assertTrue("Server session is not a watcher",
                isServerSessionWatcher(zk2.getSessionId(), "/node1",
                WatcherType.Data));
        removeAllWatches(zk2, "/node1", WatcherType.Data, false, Code.OK);
        Assert.assertTrue("Didn't remove data watcher",
                rmWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));

        Assert.assertFalse("Server session is still a watcher after removal",
                isServerSessionWatcher(zk2.getSessionId(), "/node1",
                WatcherType.Data));
    }

};