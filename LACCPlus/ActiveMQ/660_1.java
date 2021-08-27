//,temp,WSTransportLinkStealingTest.java,65,75,temp,LegacyStoreReplayer.java,77,89
//,3
public class xxx {
    protected void stopBroker() {
        try {
            if (broker != null) {
                broker.stop();
                broker.waitUntilStopped();
                broker = null;
            }
        } catch (Exception e) {
            LOG.warn("Error during Broker stop");
        }
    }

};