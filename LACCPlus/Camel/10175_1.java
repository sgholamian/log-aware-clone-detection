//,temp,DefaultHttpRegistry.java,101,113,temp,DefaultHttpRegistry.java,64,75
//,2
public class xxx {
    @Override
    public void register(HttpRegistryProvider provider) {
        synchronized (lock) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Registering CamelServlet with name {} consumers present: {}", provider.getServletName(),
                        consumers.size());
            }
            providers.add(provider);
            for (HttpConsumer consumer : consumers) {
                provider.connect(consumer);
            }
        }
    }

};