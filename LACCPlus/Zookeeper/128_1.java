//,temp,RemoveWatchesTest.java,652,700,temp,RemoveWatchesTest.java,617,646
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testManyWatchersWhenNoConnection() throws Exception {
        int count = 3;
        List<MyWatcher> wList = new ArrayList<MyWatcher>(count);
        MyWatcher w;
        String path = "/node";

        // Child watcher
        for (int i = 0; i < count; i++) {
            String nodePath = path + i;
            zk1.create(nodePath, null, Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            nodePath += "/";
        }
        for (int i = 0; i < count; i++) {
            String nodePath = path + i;
            w = new MyWatcher(path + i, 2);
            wList.add(w);
            LOG.info("Adding child watcher {} on path {}", new Object[] { w,
                    nodePath });
            zk1.getChildren(nodePath, w);
            nodePath += "/";
        }
        Assert.assertEquals("Failed to add watchers!", count, zk1
                .getChildWatches().size());

        // Data watcher
        for (int i = 0; i < count; i++) {
            String nodePath = path + i;
            w = wList.get(i);
            LOG.info("Adding data watcher {} on path {}", new Object[] { w,
                    nodePath });
            zk1.getData(nodePath, w, null);
            nodePath += "/";
        }
        Assert.assertEquals("Failed to add watchers!", count, zk1
                .getDataWatches().size());
        stopServer();
        for (int i = 0; i < count; i++) {
            final MyWatcher watcher = wList.get(i);
            removeWatches(zk1, path + i, watcher, WatcherType.Any, true,
                    Code.OK);
            Assert.assertTrue("Didn't remove watcher", watcher.matches());
        }
        Assert.assertEquals("Didn't remove watch references!", 0, zk1
                .getChildWatches().size());
        Assert.assertEquals("Didn't remove watch references!", 0, zk1
                .getDataWatches().size());
    }

};