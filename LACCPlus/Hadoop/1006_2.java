//,temp,RollingLevelDB.java,343,360,temp,AbstractLivelinessMonitor.java,112,139
//,3
public class xxx {
    @Override
    public void run() {
      while (!stopped && !Thread.currentThread().isInterrupted()) {
        synchronized (AbstractLivelinessMonitor.this) {
          Iterator<Map.Entry<O, Long>> iterator = 
            running.entrySet().iterator();

          //avoid calculating current time everytime in loop
          long currentTime = clock.getTime();

          while (iterator.hasNext()) {
            Map.Entry<O, Long> entry = iterator.next();
            if (currentTime > entry.getValue() + expireInterval) {
              iterator.remove();
              expire(entry.getKey());
              LOG.info("Expired:" + entry.getKey().toString() + 
                      " Timed out after " + expireInterval/1000 + " secs");
            }
          }
        }
        try {
          Thread.sleep(monitorInterval);
        } catch (InterruptedException e) {
          LOG.info(getName() + " thread interrupted");
          break;
        }
      }
    }

};