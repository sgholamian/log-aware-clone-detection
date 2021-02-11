//,temp,TestBookKeeperJournalManager.java,947,963,temp,TestBookKeeperSpeculativeRead.java,150,166
//,2
public class xxx {
  private void sleepBookie(final CountDownLatch l, final BookieServer bookie)
      throws Exception {

    Thread sleeper = new Thread() {
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
    sleeper.setName("BookieServerSleeper-" + bookie.getBookie().getId());
    sleeper.start();
  }

};