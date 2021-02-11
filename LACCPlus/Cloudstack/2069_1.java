//,temp,SimulatorImageStoreLifeCycleImpl.java,56,99,temp,CloudStackImageStoreLifeCycleImpl.java,77,138
//,3
public class xxx {
    @Override
    public DataStore initialize(Map<String, Object> dsInfos) {
        Long dcId = (Long)dsInfos.get("zoneId");
        String url = (String)dsInfos.get("url");
        String name = (String)dsInfos.get("name");
        if (name == null) {
            name = url;
        }
        String providerName = (String)dsInfos.get("providerName");
        DataStoreRole role = (DataStoreRole)dsInfos.get("role");
        Map<String, String> details = (Map<String, String>)dsInfos.get("details");

        s_logger.info("Trying to add a new data store at " + url + " to data center " + dcId);

        URI uri;
        try {
            uri = new URI(UriUtils.encodeURIComponent(url));
            if (uri.getScheme() == null) {
                throw new InvalidParameterValueException("uri.scheme is null " + url + ", add nfs:// as a prefix");
            } else if (uri.getScheme().equalsIgnoreCase("nfs")) {
                if (uri.getHost() == null || uri.getHost().equalsIgnoreCase("") || uri.getPath() == null || uri.getPath().equalsIgnoreCase("")) {
                    throw new InvalidParameterValueException("Your host and/or path is wrong.  Make sure it is of the format nfs://hostname/path");
                }
            }
        } catch (URISyntaxException e) {
            throw new InvalidParameterValueException(url + " is not a valid uri");
        }

        if (dcId == null) {
            throw new InvalidParameterValueException("DataCenter id is null, and simulator image store has to be associated with a data center");
        }

        Map<String, Object> imageStoreParameters = new HashMap<String, Object>();
        imageStoreParameters.put("name", name);
        imageStoreParameters.put("zoneId", dcId);
        imageStoreParameters.put("url", url);
        imageStoreParameters.put("protocol", uri.getScheme().toLowerCase());
        imageStoreParameters.put("scope", ScopeType.ZONE);
        imageStoreParameters.put("providerName", providerName);
        imageStoreParameters.put("role", role);

        ImageStoreVO ids = imageStoreHelper.createImageStore(imageStoreParameters, details);
        return imageStoreMgr.getImageStore(ids.getId());
    }

};