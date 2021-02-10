//,temp,ApplicationMaster.java,1211,1217,temp,BackupImage.java,323,328
//,3
public class xxx {
  private synchronized void setState(BNState newState) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("State transition " + bnState + " -> " + newState);
    }
    bnState = newState;
  }

};