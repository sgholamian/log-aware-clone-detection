//,temp,DefaultHttpRegistry.java,115,123,temp,DefaultHttpRegistry.java,77,88
//,3
public class xxx {
    @Override
    public void unregister(HttpRegistryProvider provider) {
        synchronized (lock) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unregistering CamelServlet with name {}", provider.getServletName());
            }
            providers.remove(provider);
        }
    }

};