//,temp,RemoveWatchesCmdTest.java,198,231,temp,RemoveWatchesCmdTest.java,64,98
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testRemoveWatchesWithNoPassedOptions() throws Exception {
        List<EventType> expectedEvents = new ArrayList<Watcher.Event.EventType>();
        expectedEvents.add(EventType.ChildWatchRemoved);
        expectedEvents.add(EventType.DataWatchRemoved);
        MyWatcher myWatcher = new MyWatcher("/testnode1", expectedEvents, 2);

        zk.create("/testnode1", "data".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        zk.create("/testnode2", "data".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);

        LOG.info("Adding childwatcher to /testnode1 and /testnode2");
        zk.getChildren("/testnode1", myWatcher);
        zk.getChildren("/testnode2", myWatcher);

        LOG.info("Adding datawatcher to /testnode1 and /testnode2");
        zk.getData("/testnode1", myWatcher, null);
        zk.getData("/testnode2", myWatcher, null);

        String cmdstring = "removewatches /testnode1";
        LOG.info("Remove watchers using shell command : {}", cmdstring);
        zkMain.cl.parseCommand(cmdstring);
        Assert.assertTrue("Removewatches cmd fails to remove child watches",
                zkMain.processZKCmd(zkMain.cl));
        LOG.info("Waiting for the DataWatchRemoved event");
        myWatcher.matches();

        // verifying that other path child watches are not affected
        Assert.assertTrue(
                "Failed to find child watches for the path testnode2", zk
                        .getChildWatches().contains("/testnode2"));
        Assert.assertTrue("Failed to find data watches for the path testnode2",
                zk.getDataWatches().contains("/testnode2"));
    }

};