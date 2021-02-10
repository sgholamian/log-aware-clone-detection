//,temp,SecondaryNameNode.java,320,355,temp,FsDatasetImpl.java,2088,2121
//,3
public class xxx {
  public void shutdown() {
    shouldRun = false;
    if (checkpointThread != null) {
      checkpointThread.interrupt();
      try {
        checkpointThread.join(10000);
      } catch (InterruptedException e) {
        LOG.info("Interrupted waiting to join on checkpointer thread");
        Thread.currentThread().interrupt(); // maintain status
      }
    }
    try {
      if (infoServer != null) {
        infoServer.stop();
        infoServer = null;
      }
    } catch (Exception e) {
      LOG.warn("Exception shutting down SecondaryNameNode", e);
    }
    if (nameNodeStatusBeanName != null) {
      MBeans.unregister(nameNodeStatusBeanName);
      nameNodeStatusBeanName = null;
    }
    try {
      if (checkpointImage != null) {
        checkpointImage.close();
        checkpointImage = null;
      }
    } catch(IOException e) {
      LOG.warn("Exception while closing CheckpointStorage", e);
    }
    if (namesystem != null) {
      namesystem.shutdown();
      namesystem = null;
    }
  }

};