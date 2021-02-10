//,temp,LeveldbTimelineStore.java,274,288,temp,RollingLevelDBTimelineStore.java,359,377
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
        LOG.warn("Interrupted while waiting for deletion thread to complete,"
            + " closing db now", e);
      }
    }
    IOUtils.cleanupWithLogger(LOG, domaindb);
    IOUtils.cleanupWithLogger(LOG, starttimedb);
    IOUtils.cleanupWithLogger(LOG, ownerdb);
    entitydb.stop();
    indexdb.stop();
    super.serviceStop();
  }

};