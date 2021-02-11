//,temp,TestBookKeeperJournalManager.java,947,963,temp,TestBookKeeperSpeculativeRead.java,150,166
//,2
public class xxx {
  private void sleepBookie(final CountDownLatch latch, final BookieServer bookie)
      throws Exception {

    Thread sleeper = new Thread() {
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
    sleeper.setName("BookieServerSleeper-" + bookie.getBookie().getId());
    sleeper.start();
  }

};