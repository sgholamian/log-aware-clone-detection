//,temp,NameNode.java,955,962,temp,FsDatasetImpl.java,735,743
//,3
public class xxx {
  private void stopAtException(Exception e){
    try {
      this.stop();
    } catch (Exception ex) {
      LOG.warn("Encountered exception when handling exception ("
          + e.getMessage() + "):", ex);
    }
  }

};