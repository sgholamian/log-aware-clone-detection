//,temp,RemoveWatchesTest.java,478,502,temp,RemoveWatchesTest.java,251,277
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testRemoveAnyChildWatcher() throws Exception {
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
        zk2.getChildren("/node1", w2);
        LOG.info("Adding child watcher {} on path {}", new Object[] { w2,
                "/node1" });
        zk2.getChildren("/node1", w1);
        removeWatches(zk2, "/node1", w2, WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove child watcher", w2.matches());
        Assert.assertEquals("Didn't find child watcher", 1, zk2
                .getChildWatches().size());
        Assert.assertEquals("Didn't find data watcher", 1, zk2
                .getDataWatches().size());
        removeWatches(zk2, "/node1", w1, WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove watchers", w1.matches());
    }

};