//,temp,SessionTest.java,359,392,temp,SessionTest.java,324,347
//,3
public class xxx {
    @Test
    public void testMinMaxSessionTimeout() throws Exception {
        // override the defaults
        final int MINSESS = 20000;
        final int MAXSESS = 240000;
        {
            ZooKeeperServer zs = ClientBase.getServer(serverFactory);
            zs.setMinSessionTimeout(MINSESS);
            zs.setMaxSessionTimeout(MAXSESS);
        }

        // validate typical case - requested == negotiated
        int timeout = 120000;
        DisconnectableZooKeeper zk = createClient(timeout);
        Assert.assertEquals(timeout, zk.getSessionTimeout());
        // make sure tostring works in both cases
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());

        // validate lower limit
        zk = createClient(MINSESS/2);
        Assert.assertEquals(MINSESS, zk.getSessionTimeout());
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());

        // validate upper limit
        zk = createClient(MAXSESS * 2);
        Assert.assertEquals(MAXSESS, zk.getSessionTimeout());
        LOG.info(zk.toString());
        zk.close();
        LOG.info(zk.toString());
    }

};