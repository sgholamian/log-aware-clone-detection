//,temp,NetworkTagMappingManagerFactory.java,40,48,temp,RMStateStoreFactory.java,29,35
//,2
public class xxx {
  public static NetworkTagMappingManager getManager(Configuration conf) {
    Class<? extends NetworkTagMappingManager> managerClass =
        conf.getClass(YarnConfiguration.NM_NETWORK_TAG_MAPPING_MANAGER,
            NetworkTagMappingJsonManager.class,
            NetworkTagMappingManager.class);
    LOG.info("Using NetworkTagMappingManager implementation - "
        + managerClass);
    return ReflectionUtils.newInstance(managerClass, conf);
  }

};