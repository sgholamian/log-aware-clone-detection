//,temp,sample_3146.java,2,9,temp,sample_8094.java,2,9
//,3
public class xxx {
protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
String cacheKey = extractCacheKeyFromUri(uri);
if (endpointCache.containsKey(cacheKey)) {


log.info("using cached endpoint for uri");
}
}

};