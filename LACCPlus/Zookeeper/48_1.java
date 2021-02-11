//,temp,RemoveWatchesTest.java,617,646,temp,RemoveWatchesTest.java,547,572
//,3
public class xxx {
    @Test(timeout = 90000)
    public void testManyDataWatchers() throws Exception {
        int count = 50;
        List<MyWatcher> wList = new ArrayList<MyWatcher>(count);
        MyWatcher w;
        String path = "/node";

        // Data watcher
        for (int i = 0; i < count; i++) {
            String nodePath = path + i;
            w = new MyWatcher(path + i, 1);
            wList.add(w);
            zk1.create(nodePath, null, Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            LOG.info("Adding data watcher {} on path {}", new Object[] { w,
                    nodePath });
            zk1.getData(nodePath, w, null);
            nodePath += "/";
        }
        Assert.assertEquals("Failed to add watchers!", count, zk1
                .getDataWatches().size());
        for (int i = 0; i < count; i++) {
            final MyWatcher watcher = wList.get(i);
            removeWatches(zk1, path + i, watcher, WatcherType.Data, false,
                    Code.OK);
            Assert.assertTrue("Didn't remove data watcher", watcher.matches());
        }
        Assert.assertEquals("Didn't remove watch references!", 0, zk1
                .getDataWatches().size());
    }

};