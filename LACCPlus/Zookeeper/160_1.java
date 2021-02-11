//,temp,RemoveWatchesCmdTest.java,198,231,temp,RemoveWatchesCmdTest.java,64,98
//,3
public class xxx {
    @Test(timeout = 30000)
    public void testRemoveNodeDeletedWatches() throws Exception {
        LOG.info("Adding NodeDeleted watcher");
        List<EventType> expectedEvents = new ArrayList<Watcher.Event.EventType>();
        expectedEvents.add(EventType.ChildWatchRemoved);
        expectedEvents.add(EventType.NodeDeleted);
        MyWatcher myWatcher = new MyWatcher("/testnode1", expectedEvents, 1);

        zk.create("/testnode1", "data".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        zk.create("/testnode1/testnode2", "data".getBytes(),
                Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.getChildren("/testnode1/testnode2", myWatcher);
        zk.getChildren("/testnode1", myWatcher);

        String cmdstring = "removewatches /testnode1 -c";
        LOG.info("Remove watchers using shell command : {}", cmdstring);
        zkMain.cl.parseCommand(cmdstring);
        Assert.assertTrue("Removewatches cmd fails to remove child watches",
                zkMain.processZKCmd(zkMain.cl));
        LOG.info("Waiting for the ChildWatchRemoved event");
        myWatcher.matches();
        Assert.assertEquals(
                "Failed to remove child watches : " + zk.getChildWatches(), 1,
                zk.getChildWatches().size());

        Assert.assertTrue(
                "Failed to remove child watches :" + zk.getChildWatches(), zk
                        .getChildWatches().contains("/testnode1/testnode2"));

        // verify node delete watcher
        zk.delete("/testnode1/testnode2", -1);
        myWatcher.matches();
    }

};