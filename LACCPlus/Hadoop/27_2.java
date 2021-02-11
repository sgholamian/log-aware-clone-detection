//,temp,LeveldbTimelineStore.java,286,300,temp,RollingLevelDBTimelineStore.java,382,397
//,3
public class xxx {
    @Override
    public void run() {
      Thread.currentThread().setName("Leveldb Timeline Store Retention");
      while (true) {
        long timestamp = System.currentTimeMillis() - ttl;
        try {
          discardOldEntities(timestamp);
          Thread.sleep(ttlInterval);
        } catch (IOException e) {
          LOG.error(e);
        } catch (InterruptedException e) {
          LOG.info("Deletion thread received interrupt, exiting");
          break;
        }
      }
    }

};