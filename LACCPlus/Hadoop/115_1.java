//,temp,ResourceManager.java,1265,1278,temp,ResourceManager.java,1252,1263
//,3
public class xxx {
  private static void removeApplication(Configuration conf, String applicationId)
      throws Exception {
    RMStateStore rmStore = RMStateStoreFactory.getStore(conf);
    rmStore.init(conf);
    rmStore.start();
    try {
      ApplicationId removeAppId = ConverterUtils.toApplicationId(applicationId);
      LOG.info("Deleting application " + removeAppId + " from state store");
      rmStore.removeApplication(removeAppId);
      LOG.info("Application is deleted from state store");
    } finally {
      rmStore.stop();
    }
  }

};