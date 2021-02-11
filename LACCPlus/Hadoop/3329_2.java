//,temp,NetworkTagMappingManagerFactory.java,40,48,temp,RMStateStoreFactory.java,29,35
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