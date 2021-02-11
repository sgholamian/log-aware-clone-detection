//,temp,S3ImageStoreLifeCycleImpl.java,70,102,temp,SwiftImageStoreLifeCycleImpl.java,57,87
//,3
public class xxx {
    @Override
    public DataStore initialize(Map<String, Object> dsInfos) {

        Long dcId = (Long)dsInfos.get("zoneId");
        String url = (String)dsInfos.get("url");
        String name = (String)dsInfos.get("name");
        ScopeType scope = (ScopeType)dsInfos.get("scope");
        String providerName = (String)dsInfos.get("providerName");
        DataStoreRole role = (DataStoreRole)dsInfos.get("role");

        Map<String, String> details = (Map<String, String>)dsInfos.get("details");

        s_logger.info("Trying to add a swift store at " + url + " in data center " + dcId);

        // just need to insert an entry in DB
        Map<String, Object> imageStoreParameters = new HashMap<String, Object>();
        imageStoreParameters.put("name", name);
        imageStoreParameters.put("zoneId", dcId);
        imageStoreParameters.put("url", url);
        imageStoreParameters.put("protocol", "http");
        if (scope != null) {
            imageStoreParameters.put("scope", scope);
        } else {
            imageStoreParameters.put("scope", ScopeType.REGION);
        }
        imageStoreParameters.put("providerName", providerName);
        imageStoreParameters.put("role", role);

        ImageStoreVO ids = imageStoreHelper.createImageStore(imageStoreParameters, details);
        return imageStoreMgr.getImageStore(ids.getId());
    }

};