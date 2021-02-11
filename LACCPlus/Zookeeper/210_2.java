//,temp,SessionTest.java,359,392,temp,SessionTest.java,324,347
//,3
public class xxx {
    @Test
    public void testSessionTimeoutAccess() throws Exception {
        // validate typical case - requested == negotiated
        DisconnectableZooKeeper zk = createClient(TICK_TIME * 4);
        Assert.assertEquals(TICK_TIME * 4, zk.getSessionTimeout());
        // make sure tostring works in both cases
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());

        // validate lower limit
        zk = createClient(TICK_TIME);
        Assert.assertEquals(TICK_TIME * 2, zk.getSessionTimeout());
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());

        // validate upper limit
        zk = createClient(TICK_TIME * 30);
        Assert.assertEquals(TICK_TIME * 20, zk.getSessionTimeout());
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());
    }

};