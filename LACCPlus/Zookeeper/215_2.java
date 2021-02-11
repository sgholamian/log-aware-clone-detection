//,temp,RemoveWatchesTest.java,211,245,temp,RemoveWatchesTest.java,173,205
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testMultipleDataWatchers() throws IOException,
            InterruptedException, KeeperException {
        zk1.create("/node1", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        MyWatcher w1 = new MyWatcher("/node1", 1);
        LOG.info("Adding data watcher {} on path {}", new Object[] { w1,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w1));
        MyWatcher w2 = new MyWatcher("/node1", 1);
        LOG.info("Adding data watcher {} on path {}", new Object[] { w2,
                "/node1" });
        Assert.assertNotNull("Didn't set data watches",
                zk2.exists("/node1", w2));
        removeWatches(zk2, "/node1", w2, WatcherType.Data, false, Code.OK);
        Assert.assertEquals("Didn't find data watcher", 1,
                zk2.getDataWatches().size());
        Assert.assertEquals("Didn't find data watcher", "/node1",
                zk2.getDataWatches().get(0));
        removeWatches(zk2, "/node1", w1, WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove data watcher", w2.matches());
        // closing session should remove ephemeral nodes and trigger data
        // watches if any
        if (zk1 != null) {
            zk1.close();
            zk1 = null;
        }

        List<EventType> events = w2.getEventsAfterWatchRemoval();
        Assert.assertEquals(
                "Shouldn't get NodeDeletedEvent after watch removal", 0,
                events.size());
    }

};