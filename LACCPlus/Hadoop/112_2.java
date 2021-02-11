//,temp,MiniYARNCluster.java,563,584,temp,MiniYARNCluster.java,312,341
//,3
public class xxx {
  private synchronized void startResourceManager(final int index) {
    try {
      Thread rmThread = new Thread() {
        public void run() {
          resourceManagers[index].start();
        }
      };
      rmThread.setName("RM-" + index);
      rmThread.start();
      int waitCount = 0;
      while (resourceManagers[index].getServiceState() == STATE.INITED
          && waitCount++ < 60) {
        LOG.info("Waiting for RM to start...");
        Thread.sleep(1500);
      }
      if (resourceManagers[index].getServiceState() != STATE.STARTED) {
        // RM could have failed.
        throw new IOException(
            "ResourceManager failed to start. Final state is "
                + resourceManagers[index].getServiceState());
      }
    } catch (Throwable t) {
      throw new YarnRuntimeException(t);
    }
    Configuration conf = resourceManagers[index].getConfig();
    LOG.info("MiniYARN ResourceManager address: " +
        conf.get(YarnConfiguration.RM_ADDRESS));
    LOG.info("MiniYARN ResourceManager web address: " +
        WebAppUtils.getRMWebAppURLWithoutScheme(conf));
  }

};