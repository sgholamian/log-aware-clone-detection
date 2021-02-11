//,temp,RemoveWatchesTest.java,870,911,temp,RemoveWatchesTest.java,825,864
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveWhenMultipleDataWatchesOnAPath() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        final CountDownLatch dataWatchCount = new CountDownLatch(1);
        final CountDownLatch rmWatchCount = new CountDownLatch(1);
        Watcher w1 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == EventType.DataWatchRemoved) {
                    rmWatchCount.countDown();
                }
            }
        };
        Watcher w2 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == EventType.NodeDataChanged) {
                    dataWatchCount.countDown();
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

        removeWatches(zk2, "/node1", w1, WatcherType.Data, false, Code.OK);
        Assert.assertTrue("Didn't remove data watcher",
                rmWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));

        zk1.setData("/node1", "test".getBytes(), -1);
        LOG.info("Waiting for data watchers to be notified");
        Assert.assertTrue("Didn't get data watch notification!",
                dataWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));
    }

};