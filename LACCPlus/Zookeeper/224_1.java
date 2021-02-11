//,temp,RemoveWatchesTest.java,705,741,temp,RemoveWatchesTest.java,251,277
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testChRootRemoveWatcher() throws Exception {
        // creating the subtree for chRoot clients.
        String chRoot = "/appsX";
        zk1.create("/appsX", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        if (zk1 != null) {
            zk1.close();
        }
        if (zk2 != null) {
            zk2.close();
        }
        // Creating chRoot client.
        zk1 = createClient(this.hostPort + chRoot);
        zk2 = createClient(this.hostPort + chRoot);

        LOG.info("Creating child znode /node1 using chRoot client");
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
        removeWatches(zk2, "/node1", w1, WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove child watcher", w1.matches());
        Assert.assertEquals("Didn't find child watcher", 1, zk2
                .getChildWatches().size());
        removeWatches(zk2, "/node1", w2, WatcherType.Any, false, Code.OK);
        Assert.assertTrue("Didn't remove child watcher", w2.matches());
    }

};