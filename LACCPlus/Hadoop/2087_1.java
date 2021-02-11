//,temp,TestDistributedShell.java,74,122,temp,TestUnmanagedAMLauncher.java,58,98
//,3
public class xxx {
  protected void setupInternal(int numNodeManager) throws Exception {

    LOG.info("Starting up YARN cluster");
    
    conf = new YarnConfiguration();
    conf.setInt(YarnConfiguration.RM_SCHEDULER_MINIMUM_ALLOCATION_MB, 128);
    conf.set("yarn.log.dir", "target");
    conf.setBoolean(YarnConfiguration.TIMELINE_SERVICE_ENABLED, true);
    conf.set(YarnConfiguration.RM_SCHEDULER, CapacityScheduler.class.getName());
    conf.setBoolean(YarnConfiguration.NODE_LABELS_ENABLED, true);
    
    if (yarnCluster == null) {
      yarnCluster =
          new MiniYARNCluster(TestDistributedShell.class.getSimpleName(), 1,
              numNodeManager, 1, 1);
      yarnCluster.init(conf);
      
      yarnCluster.start();
      
      waitForNMsToRegister();
      
      URL url = Thread.currentThread().getContextClassLoader().getResource("yarn-site.xml");
      if (url == null) {
        throw new RuntimeException("Could not find 'yarn-site.xml' dummy file in classpath");
      }
      Configuration yarnClusterConfig = yarnCluster.getConfig();
      yarnClusterConfig.set("yarn.application.classpath", new File(url.getPath()).getParent());
      //write the document to a buffer (not directly to the file, as that
      //can cause the file being written to get read -which will then fail.
      ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
      yarnClusterConfig.writeXml(bytesOut);
      bytesOut.close();
      //write the bytes to the file in the classpath
      OutputStream os = new FileOutputStream(new File(url.getPath()));
      os.write(bytesOut.toByteArray());
      os.close();
    }
    FileContext fsContext = FileContext.getLocalFSFileContext();
    fsContext
        .delete(
            new Path(conf
                .get("yarn.timeline-service.leveldb-timeline-store.path")),
            true);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      LOG.info("setup thread sleep interrupted. message=" + e.getMessage());
    }
  }

};