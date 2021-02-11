//,temp,TestBookKeeperJournalManager.java,951,959,temp,TestBookKeeperSpeculativeRead.java,154,162
//,2
public class xxx {
      public void run() {
        try {
          bookie.suspendProcessing();
          l.await(60, TimeUnit.SECONDS);
          bookie.resumeProcessing();
        } catch (Exception e) {
          LOG.error("Error suspending bookie", e);
        }
      }

};