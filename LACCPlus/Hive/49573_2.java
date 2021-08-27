//,temp,SessionHiveMetaStoreClient.java,2286,2306,temp,SessionHiveMetaStoreClient.java,2047,2066
//,3
public class xxx {
  @Override
  protected String getConfigValueInternal(String name, String defaultValue)
      throws TException, ConfigValSecurityException {
    Map<Object, Object> queryCache = getQueryCache();
    if (queryCache != null) {
      // Retrieve or populate cache
      CacheKey cacheKey = new CacheKey(KeyType.CONFIG_VALUE, name, defaultValue);
      String v = (String) queryCache.get(cacheKey);
      if (v == null) {
        v = super.getConfigValueInternal(name, defaultValue);
        queryCache.put(cacheKey, v);
      } else {
        LOG.debug(
            "Query level HMS cache: method=getConfigValueInternal, name={}",
            name);
      }
      return v;
    }
    return super.getConfigValueInternal(name, defaultValue);
  }

};