//,temp,QuorumTest.java,195,237,temp,SessionTest.java,245,287
//,3
public class xxx {
    @Test
    public void testSessionMove() throws Exception {
        String hostPorts[] = HOSTPORT.split(",");
        DisconnectableZooKeeper zk = new DisconnectableZooKeeper(hostPorts[0],
                CONNECTION_TIMEOUT, new MyWatcher("0"));
        zk.create("/sessionMoveTest", new byte[0], Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        // we want to loop through the list twice
        for(int i = 0; i < hostPorts.length*2; i++) {
            zk.dontReconnect();
            // This should stomp the zk handle
            DisconnectableZooKeeper zknew = new DisconnectableZooKeeper(
                    hostPorts[(i+1)%hostPorts.length],
                    CONNECTION_TIMEOUT,
                    new MyWatcher(Integer.toString(i+1)),
                    zk.getSessionId(),
                    zk.getSessionPasswd());
            final int result[] = new int[1];
            result[0] = Integer.MAX_VALUE;
            zknew.sync("/", new AsyncCallback.VoidCallback() {
                    public void processResult(int rc, String path, Object ctx) {
                        synchronized(result) { result[0] = rc; result.notify(); }
                    }
                }, null);
            synchronized(result) {
                if(result[0] == Integer.MAX_VALUE) {
                    result.wait(5000);
                }
            }
            LOG.info(hostPorts[(i+1)%hostPorts.length] + " Sync returned " + result[0]);
            Assert.assertTrue(result[0] == KeeperException.Code.OK.intValue());
            zknew.setData("/", new byte[1], -1);
            try {
                zk.setData("/", new byte[1], -1);
                Assert.fail("Should have lost the connection");
            } catch(KeeperException.ConnectionLossException e) {
                LOG.info("Got connection loss exception as expected");
            }
            //zk.close();
            zk = zknew;
        }
        zk.close();
    }

};