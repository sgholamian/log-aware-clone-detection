//,temp,DefaultHttpRegistry.java,115,123,temp,DefaultHttpRegistry.java,77,88
//,3
public class xxx {
    @Override
    public void unregister(HttpConsumer consumer) {
        synchronized (lock) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unregistering consumer for path {}", consumer.getPath());
            }
            consumers.remove(consumer);
            for (HttpRegistryProvider provider : providers) {
                provider.disconnect(consumer);
            }
        }
    }

};