//,temp,RemoveWatchesTest.java,1039,1109,temp,RemoveWatchesTest.java,978,1033
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveAllWatchesOnAPath() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        final CountDownLatch watchCount = new CountDownLatch(2);
        final CountDownLatch rmWatchCount = new CountDownLatch(4);
        Watcher w1 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                switch (event.getType()) {
                case ChildWatchRemoved:
                case DataWatchRemoved:
                    rmWatchCount.countDown();
                    break;
                case NodeChildrenChanged:
                case NodeDataChanged:
                    watchCount.countDown();
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
                case ChildWatchRemoved:
                case DataWatchRemoved:
                    rmWatchCount.countDown();
                    break;
                case NodeChildrenChanged:
                case NodeDataChanged:
                    watchCount.countDown();
                    break;
                default:
                    break;
                }
            }
        };
        // Add multiple child watches
        LOG.info("Adding child watcher {} on path {}", new Object[] { w1,
                "/node1" });
        Assert.assertEquals("Didn't set child watches", 0,
                zk2.getChildren("/node1", w1).size());
        LOG.info("Adding child watcher {} on path {}", new Object[] { w2,
                "/node1" });
        Assert.assertEquals("Didn't set child watches", 0,
                zk2.getChildren("/node1", w2).size());

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
        removeAllWatches(zk2, "/node1", WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove data watcher",
                rmWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));
        Assert.assertFalse("Server session is still a watcher after removal",
                isServerSessionWatcher(zk2.getSessionId(), "/node1",
                WatcherType.Data));
        Assert.assertEquals("Received watch notification after removal!", 2,
                watchCount.getCount());
    }

};