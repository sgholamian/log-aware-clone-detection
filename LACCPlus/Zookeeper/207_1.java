//,temp,RemoveWatchesCmdTest.java,173,193,temp,RemoveWatchesCmdTest.java,103,127
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testRemoveNodeChildrenChangedWatches() throws Exception {
        List<EventType> expectedEvents = new ArrayList<Watcher.Event.EventType>();
        expectedEvents.add(EventType.ChildWatchRemoved);
        MyWatcher myWatcher = new MyWatcher("/testnode1", expectedEvents, 1);

        zk.create("/testnode1", "data".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        LOG.info("Adding child changed watcher");
        zk.getChildren("/testnode1", myWatcher);

        String cmdstring = "removewatches /testnode1 -c";
        LOG.info("Remove watchers using shell command : {}", cmdstring);
        zkMain.cl.parseCommand(cmdstring);
        Assert.assertTrue("Removewatches cmd fails to remove child watches",
                zkMain.processZKCmd(zkMain.cl));
        myWatcher.matches();
        Assert.assertEquals(
                "Failed to remove child watches : " + zk.getChildWatches(), 0,
                zk.getChildWatches().size());
    }

};