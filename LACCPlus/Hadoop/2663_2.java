//,temp,FairScheduler.java,644,653,temp,NameNode.java,955,962
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