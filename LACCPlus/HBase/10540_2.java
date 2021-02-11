//,temp,AsyncRegionLocator.java,152,161,temp,DeletionListener.java,91,100
//,3
public class xxx {
  @Override
  public void nodeDeleted(String path) {
    if (!path.equals(pathToWatch)) {
      return;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Processing delete on " + pathToWatch);
    }
    deletedLatch.countDown();
  }

};