//,temp,JaasDualAuthenticationNetworkBridgeTest.java,89,106,temp,NetworkConnectionsTest.java,229,244
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        LOG.info("Shutting down");
        if (broker1 != null && broker1.isStarted()) {
            LOG.info("Broker still running, stopping it now.");
            broker1.stop();
        }
        else {
            LOG.info("Broker1 not running, nothing to shutdown.");
        }
        if (broker2 != null && broker2.isStarted()) {
            LOG.info("Broker still running, stopping it now.");
            broker2.stop();
        }
        else {
            LOG.info("Broker2 not running, nothing to shutdown.");
        }
    }

};