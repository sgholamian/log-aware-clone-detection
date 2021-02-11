//,temp,LeveldbRMStateStore.java,859,870,temp,NMLeveldbStateStoreService.java,1596,1607
//,2
public class xxx {
    @Override
    public void run() {
      long start = Time.monotonicNow();
      LOG.info("Starting full compaction cycle");
      try {
        db.compactRange(null, null);
      } catch (DBException e) {
        LOG.error("Error compacting database", e);
      }
      long duration = Time.monotonicNow() - start;
      LOG.info("Full compaction cycle completed in " + duration + " msec");
    }

};