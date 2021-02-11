//,temp,sample_8657.java,2,7,temp,sample_3532.java,2,11
//,3
public class xxx {
public static YarnConfigurationStore getStore(Configuration conf) {
Class<? extends YarnConfigurationStore> storeClass = conf.getClass(YarnConfiguration.SCHEDULER_CONFIGURATION_STORE_CLASS, InMemoryConfigurationStore.class, YarnConfigurationStore.class);


log.info("using yarnconfigurationstore implementation");
}

};