//,temp,RemoveWatchesTest.java,978,1033,temp,RemoveWatchesTest.java,870,911
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveAllChildWatchesOnAPath() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        final CountDownLatch cWatchCount = new CountDownLatch(2);
        final CountDownLatch rmWatchCount = new CountDownLatch(2);
        Watcher w1 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                switch (event.getType()) {
                case ChildWatchRemoved:
                    rmWatchCount.countDown();
                    break;
                case NodeChildrenChanged:
                    cWatchCount.countDown();
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
                    rmWatchCount.countDown();
                    break;
                case NodeChildrenChanged:
                    cWatchCount.countDown();
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

        Assert.assertTrue("Server session is not a watcher",
                isServerSessionWatcher(zk2.getSessionId(), "/node1",
                WatcherType.Children));
        removeAllWatches(zk2, "/node1", WatcherType.Children, false, Code.OK);
        Assert.assertTrue("Didn't remove child watcher",
                rmWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));

        Assert.assertFalse("Server session is still a watcher after removal",
                isServerSessionWatcher(zk2.getSessionId(), "/node1",
                WatcherType.Children));
    }

};