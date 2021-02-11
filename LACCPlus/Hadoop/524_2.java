//,temp,TestBookKeeperJournalManager.java,951,959,temp,TestBookKeeperSpeculativeRead.java,154,162
//,2
public class xxx {
      public void run() {
        try {
          bookie.suspendProcessing();
          latch.await(2, TimeUnit.MINUTES);
          bookie.resumeProcessing();
        } catch (Exception e) {
          LOG.error("Error suspending bookie", e);
        }
      }

};