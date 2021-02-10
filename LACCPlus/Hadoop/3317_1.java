//,temp,YarnConfigurationStoreFactory.java,39,45,temp,RMStateStoreFactory.java,29,35
//,2
public class xxx {
  public static YarnConfigurationStore getStore(Configuration conf) {
    Class<? extends YarnConfigurationStore> storeClass =
        conf.getClass(YarnConfiguration.SCHEDULER_CONFIGURATION_STORE_CLASS,
            InMemoryConfigurationStore.class, YarnConfigurationStore.class);
    LOG.info("Using YarnConfigurationStore implementation - " + storeClass);
    return ReflectionUtils.newInstance(storeClass, conf);
  }

};