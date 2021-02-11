//,temp,RemoveWatchesTest.java,337,384,temp,RemoveWatchesTest.java,283,331
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveAllChildWatchers() throws IOException,
            InterruptedException, KeeperException {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        MyWatcher w1 = new MyWatcher("/node1", 1);
        MyWatcher w2 = new MyWatcher("/node1", 1);
        LOG.info("Adding data watcher {} on path {}", new Object[] { w1,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w1));
        LOG.info("Adding data watcher {} on path {}", new Object[] { w2,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w2));
        LOG.info("Adding child watcher {} on path {}", new Object[] { w1,
                "/node1" });
        zk2.getChildren("/node1", w1);
        LOG.info("Adding child watcher {} on path {}", new Object[] { w2,
                "/node1" });
        zk2.getChildren("/node1", w2);
        removeWatches(zk2, "/node1", w1, WatcherType.Children, false, Code.OK);
        removeWatches(zk2, "/node1", w2, WatcherType.Children, false, Code.OK);
        zk1.setData("/node1", "test".getBytes(), -1);
        Assert.assertTrue("Didn't remove child watcher", w1.matches());
        Assert.assertTrue("Didn't remove child watcher", w2.matches());
        // waiting for child watchers to be notified
        int count = 10;
        while (count > 0) {
            if (w1.getEventsAfterWatchRemoval().size() > 0
                    && w2.getEventsAfterWatchRemoval().size() > 0) {
                break;
            }
            count--;
            Thread.sleep(1000);
        }
        // watcher1
        List<EventType> events = w1.getEventsAfterWatchRemoval();
        Assert.assertEquals("Didn't get NodeDataChanged event", 1,
                events.size());
        Assert.assertTrue("Didn't get NodeDataChanged event",
                events.contains(EventType.NodeDataChanged));
        // watcher2
        events = w2.getEventsAfterWatchRemoval();
        Assert.assertEquals("Didn't get NodeDataChanged event", 1,
                events.size());
        Assert.assertTrue("Didn't get NodeDataChanged event",
                events.contains(EventType.NodeDataChanged));
    }

};