//,temp,DiscovererBase.java,147,169,temp,VmwareServerDiscoverer.java,732,753
//,3
public class xxx {
    @Override
    public ServerResource reloadResource(HostVO host) {
        String resourceName = host.getResource();
        ServerResource resource = getResource(resourceName);

        if (resource != null) {
            _hostDao.loadDetails(host);

            HashMap<String, Object> params = buildConfigParams(host);
            try {
                resource.configure(host.getName(), params);
            } catch (ConfigurationException e) {
                s_logger.warn("Unable to configure resource due to " + e.getMessage());
                return null;
            }
            if (!resource.start()) {
                s_logger.warn("Unable to start the resource");
                return null;
            }
        }
        return resource;
    }

};