//,temp,WSTransportLinkStealingTest.java,65,75,temp,LegacyStoreReplayer.java,77,89
//,3
public class xxx {
    public void unload() throws IOException {

        if (store != null) {
            try {
                store.stop();
            } catch (Exception e) {
                LOG.warn("Legacy store unload failed: ", e);
                throw new IOException(e);
            } finally {
                store = null;
            }
        }
    }

};