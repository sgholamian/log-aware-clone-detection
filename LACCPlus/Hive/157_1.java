//,temp,HiveMetaStoreClient.java,3328,3342,temp,HiveMetaStoreClient.java,3117,3134
//,3
public class xxx {
  @Override
  public String getConfigValue(String name, String defaultValue)
      throws TException, ConfigValSecurityException {
    long t1 = System.currentTimeMillis();

    try {
      return getConfigValueInternal(name, defaultValue);
    } finally {
      long diff = System.currentTimeMillis() - t1;
      if (LOG.isDebugEnabled()) {
        LOG.debug("class={}, method={}, duration={}, comments={}", CLASS_NAME, "getConfigValue",
            diff, "HMS client");
      }
    }
  }

};