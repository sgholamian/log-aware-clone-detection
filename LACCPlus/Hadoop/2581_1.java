//,temp,BackupImage.java,323,328,temp,SwiftNativeFileSystem.java,426,432
//,3
public class xxx {
  private synchronized void setState(BNState newState) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("State transition " + bnState + " -> " + newState);
    }
    bnState = newState;
  }

};