//,temp,MetastoreSchemaTool.java,400,410,temp,TezTask.java,436,446
//,3
public class xxx {
  private void logResources(List<LocalResource> additionalLr) {
    // log which resources we're adding (apart from the hive exec)
    if (!LOG.isDebugEnabled()) return;
    if (additionalLr == null || additionalLr.size() == 0) {
      LOG.debug("No local resources to process (other than hive-exec)");
    } else {
      for (LocalResource lr: additionalLr) {
        LOG.debug("Adding local resource: " + lr.getResource());
      }
    }
  }

};