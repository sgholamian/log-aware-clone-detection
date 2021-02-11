//,temp,ResourceManager.java,1265,1278,temp,ResourceManager.java,1252,1263
//,3
public class xxx {
  private static void deleteRMStateStore(Configuration conf) throws Exception {
    RMStateStore rmStore = RMStateStoreFactory.getStore(conf);
    rmStore.init(conf);
    rmStore.start();
    try {
      LOG.info("Deleting ResourceManager state store...");
      rmStore.deleteStore();
      LOG.info("State store deleted");
    } finally {
      rmStore.stop();
    }
  }

};