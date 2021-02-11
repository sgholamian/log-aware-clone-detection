//,temp,RemoveWatchesTest.java,109,127,temp,RemoveWatchesTest.java,88,107
//,3
public class xxx {
    private void removeWatches(ZooKeeper zk, String path, Watcher watcher,
            WatcherType watcherType, boolean local, KeeperException.Code rc)
            throws InterruptedException, KeeperException {
        LOG.info(
                "Sending removeWatches req using zk {} path: {} type: {} watcher: {} ",
                new Object[] { zk, path, watcherType, watcher });
        if (useAsync) {
            MyCallback c1 = new MyCallback(rc.intValue(), path);
            zk.removeWatches(path, watcher, watcherType, local, c1, null);
            Assert.assertTrue("Didn't succeeds removeWatch operation",
                    c1.matches());
            if (KeeperException.Code.OK.intValue() != c1.rc) {
                KeeperException ke = KeeperException
                        .create(KeeperException.Code.get(c1.rc));
                throw ke;
            }
        } else {
            zk.removeWatches(path, watcher, watcherType, local);
        }
    }

};