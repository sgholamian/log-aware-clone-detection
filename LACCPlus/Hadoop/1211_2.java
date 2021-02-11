//,temp,MetricsSinkAdapter.java,209,221,temp,LeveldbTimelineStore.java,246,260
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    if (deletionThread != null) {
      deletionThread.interrupt();
      LOG.info("Waiting for deletion thread to complete its current action");
      try {
        deletionThread.join();
      } catch (InterruptedException e) {
        LOG.warn("Interrupted while waiting for deletion thread to complete," +
            " closing db now", e);
      }
    }
    IOUtils.cleanup(LOG, db);
    super.serviceStop();
  }

};