//,temp,YarnConfigurationStoreFactory.java,39,45,temp,RMStateStoreFactory.java,29,35
//,2
public class xxx {
  public static RMStateStore getStore(Configuration conf) {
    Class<? extends RMStateStore> storeClass =
        conf.getClass(YarnConfiguration.RM_STORE,
            MemoryRMStateStore.class, RMStateStore.class);
    LOG.info("Using RMStateStore implementation - " + storeClass);
    return ReflectionUtils.newInstance(storeClass, conf);
  }

};