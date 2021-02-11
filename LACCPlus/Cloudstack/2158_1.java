//,temp,S3ImageStoreLifeCycleImpl.java,70,102,temp,SwiftImageStoreLifeCycleImpl.java,57,87
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    @Override
    public DataStore initialize(Map<String, Object> dsInfos) {

        String url = (String)dsInfos.get("url");
        String name = (String)dsInfos.get("name");
        String providerName = (String)dsInfos.get("providerName");
        ScopeType scope = (ScopeType)dsInfos.get("scope");
        DataStoreRole role = (DataStoreRole)dsInfos.get("role");
        Map<String, String> details = (Map<String, String>)dsInfos.get("details");

        s_logger.info("Trying to add a S3 store with endpoint: " + details.get(ApiConstants.S3_END_POINT));

        Map<String, Object> imageStoreParameters = new HashMap();
        imageStoreParameters.put("name", name);
        imageStoreParameters.put("url", url);
        String protocol = "http";
        String useHttps = details.get(ApiConstants.S3_HTTPS_FLAG);
        if (useHttps != null && Boolean.parseBoolean(useHttps)) {
            protocol = "https";
        }
        imageStoreParameters.put("protocol", protocol);
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