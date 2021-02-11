//,temp,RemoveWatchesCmdTest.java,173,193,temp,RemoveWatchesCmdTest.java,103,127
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testRemoveNodeDataChangedWatches() throws Exception {
        LOG.info("Adding data watcher using getData()");
        List<EventType> expectedEvents = new ArrayList<Watcher.Event.EventType>();
        expectedEvents.add(EventType.DataWatchRemoved);
        MyWatcher myWatcher = new MyWatcher("/testnode1", expectedEvents, 1);

        zk.create("/testnode1", "data".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        zk.getData("/testnode1", myWatcher, null);

        String cmdstring = "removewatches /testnode1 -d";
        LOG.info("Remove watchers using shell command : {}", cmdstring);
        zkMain.cl.parseCommand(cmdstring);
        Assert.assertTrue("Removewatches cmd fails to remove data watches",
                zkMain.processZKCmd(zkMain.cl));

        LOG.info("Waiting for the DataWatchRemoved event");
        myWatcher.matches();

        // verifying that other path data watches are removed
        Assert.assertEquals(
                "Data watches are not removed : " + zk.getDataWatches(), 0, zk
                        .getDataWatches().size());
    }

};