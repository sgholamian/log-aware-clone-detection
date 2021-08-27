//,temp,MultiKahaDBPersistenceAdapter.java,219,227,temp,MultiKahaDBPersistenceAdapter.java,209,217
//,2
public class xxx {
    private void stopAdapter(PersistenceAdapter kahaDBPersistenceAdapter, String destination) {
        try {
            kahaDBPersistenceAdapter.stop();
        } catch (Exception e) {
            RuntimeException detail = new RuntimeException("Failed to stop per destination persistence adapter for destination: " + destination + ", options:" + adapters, e);
            LOG.error(detail.toString(), e);
            throw detail;
        }
    }

};