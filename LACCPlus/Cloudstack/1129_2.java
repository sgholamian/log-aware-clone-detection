//,temp,SecondaryStorageDiscoverer.java,223,251,temp,SecondaryStorageDiscoverer.java,192,221
//,3
public class xxx {
    protected Map<? extends ServerResource, Map<String, String>> createLocalSecondaryStorageResource(long dcId, Long podId, URI uri) {
        Map<LocalSecondaryStorageResource, Map<String, String>> srs = new HashMap<LocalSecondaryStorageResource, Map<String, String>>();

        LocalSecondaryStorageResource storage = new LocalSecondaryStorageResource();
        storage = ComponentContext.inject(storage);

        Map<String, String> details = new HashMap<String, String>();

        File file = new File(uri);
        details.put("mount.path", file.getAbsolutePath());
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