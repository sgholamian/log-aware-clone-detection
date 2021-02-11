//,temp,RemoveWatchesTest.java,870,911,temp,RemoveWatchesTest.java,211,245
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveWhenMultipleChildWatchesOnAPath() throws Exception {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        final CountDownLatch childWatchCount = new CountDownLatch(1);
        final CountDownLatch rmWatchCount = new CountDownLatch(1);
        Watcher w1 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == EventType.ChildWatchRemoved) {
                    rmWatchCount.countDown();
                }
            }
        };
        Watcher w2 = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == EventType.NodeChildrenChanged) {
                    childWatchCount.countDown();
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

        removeWatches(zk2, "/node1", w1, WatcherType.Children, false, Code.OK);
        Assert.assertTrue("Didn't remove child watcher",
                rmWatchCount.await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));

        zk1.create("/node1/node2", null, Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        LOG.info("Waiting for child watchers to be notified");
        Assert.assertTrue("Didn't get child watch notification!",
                childWatchCount
                        .await(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS));
    }

};