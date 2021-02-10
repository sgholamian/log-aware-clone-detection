//,temp,SecondaryNameNode.java,320,355,temp,FsDatasetImpl.java,2088,2121
//,3
public class xxx {
  @Override // FsDatasetSpi
  public void shutdown() {
    fsRunning = false;

    if (lazyWriter != null) {
      ((LazyWriter) lazyWriter.getRunnable()).stop();
      lazyWriter.interrupt();
    }

    if (mbeanName != null) {
      MBeans.unregister(mbeanName);
    }
    
    if (asyncDiskService != null) {
      asyncDiskService.shutdown();
    }

    if (asyncLazyPersistService != null) {
      asyncLazyPersistService.shutdown();
    }
    
    if(volumes != null) {
      volumes.shutdown();
    }

    if (lazyWriter != null) {
      try {
        lazyWriter.join();
      } catch (InterruptedException ie) {
        LOG.warn("FsDatasetImpl.shutdown ignoring InterruptedException " +
                     "from LazyWriter.join");
      }
    }
  }

};