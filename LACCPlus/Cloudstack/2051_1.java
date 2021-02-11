//,temp,DummyHostDiscoverer.java,38,68,temp,SecondaryStorageDiscoverer.java,192,221
//,3
public class xxx {
    @Override
    public Map<ServerResource, Map<String, String>> find(long dcId, Long podId, Long clusterId, URI url, String username, String password, List<String> hostTags) {
        if (!url.getScheme().equals("dummy")) {
            return null;
        }

        Map<ServerResource, Map<String, String>> resources = new HashMap<ServerResource, Map<String, String>>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> details = new HashMap<String, String>();

        details.put("url", url.toString());
        details.put("username", username);
        details.put("password", password);

        params.put("url", url.toString());
        params.put("username", username);
        params.put("password", password);
        params.put("zone", Long.toString(dcId));
        params.put("guid", UUID.randomUUID().toString());
        params.put("pod", Long.toString(podId));

        DummyHostServerResource resource = new DummyHostServerResource();
        try {
            resource.configure("Dummy Host Server", params);
        } catch (ConfigurationException e) {
            s_logger.warn("Unable to instantiate dummy host server resource");
        }
        resource.start();
        resources.put(resource, details);
        return resources;
    }

};