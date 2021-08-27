//,temp,DefaultHttpRegistry.java,101,113,temp,DefaultHttpRegistry.java,64,75
//,2
public class xxx {
    @Override
    public void register(HttpConsumer consumer) {
        synchronized (lock) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Registering consumer for path {} providers present: {}", consumer.getPath(), providers.size());
            }
            consumers.add(consumer);
            for (HttpRegistryProvider provider : providers) {
                provider.connect(consumer);
            }
        }
    }

};