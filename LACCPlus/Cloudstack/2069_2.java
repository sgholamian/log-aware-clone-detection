//,temp,SimulatorImageStoreLifeCycleImpl.java,56,99,temp,CloudStackImageStoreLifeCycleImpl.java,77,138
//,3
public class xxx {
    @SuppressWarnings("unchecked")
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

        String logString = "";
        if(url.contains("cifs")) {
            logString = cleanPassword(url);
        } else {
            logString = StringUtils.cleanString(url);
        }
        s_logger.info("Trying to add a new data store at " + logString + " to data center " + dcId);

        URI uri = null;
        try {
            uri = new URI(UriUtils.encodeURIComponent(url));
            if (uri.getScheme() == null) {
                throw new InvalidParameterValueException("uri.scheme is null " + StringUtils.cleanString(url) + ", add nfs:// (or cifs://) as a prefix");
            } else if (uri.getScheme().equalsIgnoreCase("nfs")) {
                if (uri.getHost() == null || uri.getHost().equalsIgnoreCase("") || uri.getPath() == null || uri.getPath().equalsIgnoreCase("")) {
                    throw new InvalidParameterValueException("Your host and/or path is wrong.  Make sure it's of the format nfs://hostname/path");
                }
            } else if (uri.getScheme().equalsIgnoreCase("cifs")) {
                // Don't validate against a URI encoded URI.
                URI cifsUri = new URI(url);
                String warnMsg = UriUtils.getCifsUriParametersProblems(cifsUri);
                if (warnMsg != null) {
                    throw new InvalidParameterValueException(warnMsg);
                }
            }
        } catch (URISyntaxException e) {
            throw new InvalidParameterValueException(url + " is not a valid uri");
        }

        if (dcId == null) {
            throw new InvalidParameterValueException("DataCenter id is null, and cloudstack default image store has to be associated with a data center");
        }

        Map<String, Object> imageStoreParameters = new HashMap<String, Object>();
        imageStoreParameters.put("name", name);
        imageStoreParameters.put("zoneId", dcId);
        imageStoreParameters.put("url", url);
        imageStoreParameters.put("protocol", uri.getScheme().toLowerCase());
        imageStoreParameters.put("scope", ScopeType.ZONE); // default cloudstack
                                                           // provider only
                                                           // supports zone-wide
                                                           // image store
        imageStoreParameters.put("providerName", providerName);
        imageStoreParameters.put("role", role);

        ImageStoreVO ids = imageStoreHelper.createImageStore(imageStoreParameters, details);
        return imageStoreMgr.getImageStore(ids.getId());
    }

};