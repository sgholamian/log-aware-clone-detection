//,temp,FSNamesystem.java,4093,4118,temp,FSNamesystem.java,4004,4027
//,3
public class xxx {
    @Override
    public void run() {
      while (fsRunning && shouldRun) {
        try {
          long numEdits = getCorrectTransactionsSinceLastLogRoll();
          if (numEdits > rollThreshold) {
            FSNamesystem.LOG.info("NameNode rolling its own edit log because"
                + " number of edits in open segment exceeds threshold of "
                + rollThreshold);
            rollEditLog();
          }
        } catch (Exception e) {
          FSNamesystem.LOG.error("Swallowing exception in "
              + NameNodeEditLogRoller.class.getSimpleName() + ":", e);
        }
        try {
          Thread.sleep(sleepIntervalMs);
        } catch (InterruptedException e) {
          FSNamesystem.LOG.info(NameNodeEditLogRoller.class.getSimpleName()
              + " was interrupted, exiting");
          break;
        }
      }
    }

};