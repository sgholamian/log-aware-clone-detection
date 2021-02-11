//,temp,EditLogTailer_after_fix.java,272,289,temp,EditLogTailer.java,272,289
//,2
public class xxx {
  private void triggerActiveLogRoll() {
    LOG.info("Triggering log roll on remote NameNode " + activeAddr);
    try {
      getActiveNodeProxy().rollEditLog();
      lastRollTriggerTxId = lastLoadedTxnId;
    } catch (IOException ioe) {
      if (ioe instanceof RemoteException) {
        ioe = ((RemoteException)ioe).unwrapRemoteException();
        if (ioe instanceof StandbyException) {
          LOG.info("Skipping log roll. Remote node is not in Active state: " +
              ioe.getMessage().split("\n")[0]);
          return;
        }
      }

      LOG.warn("Unable to trigger a roll of the active NN", ioe);
    }
  }

};