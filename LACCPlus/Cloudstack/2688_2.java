//,temp,DummyHostDiscoverer.java,38,68,temp,SecondaryStorageDiscoverer.java,223,251
//,3
public class xxx {
    protected Map<ServerResource, Map<String, String>> createDummySecondaryStorageResource(long dcId, Long podId, URI uri) {
        Map<ServerResource, Map<String, String>> srs = new HashMap<ServerResource, Map<String, String>>();

        DummySecondaryStorageResource storage = new DummySecondaryStorageResource(_useServiceVM);
        storage = ComponentContext.inject(storage);

        Map<String, String> details = new HashMap<String, String>();

        details.put("mount.path", uri.toString());
        details.put("orig.url", uri.toString());

        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(details);
        params.put("zone", Long.toString(dcId));
        if (podId != null) {
            params.put("pod", podId.toString());
        }
        params.put("guid", uri.toString());

        try {
            storage.configure("Storage", params);
        } catch (ConfigurationException e) {
            s_logger.warn("Unable to configure the storage ", e);
            return null;
        }
        srs.put(storage, details);

        return srs;
    }

};