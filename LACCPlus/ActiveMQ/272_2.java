//,temp,JaasDualAuthenticationNetworkBridgeTest.java,89,106,temp,NetworkConnectionsTest.java,229,244
//,3
public class xxx {
    @Override
    protected void tearDown() throws Exception {
        if (localBroker.isStarted()) {
            LOG.info("Stopping LocalBroker");
            localBroker.stop();
            localBroker.waitUntilStopped();
            localBroker = null;
        }

        if (remoteBroker.isStarted()) {
            LOG.info("Stopping RemoteBroker");
            remoteBroker.stop();
            remoteBroker.waitUntilStopped();
            remoteBroker = null;
        }
    }

};