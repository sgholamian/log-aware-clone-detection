//,temp,RollingLevelDB.java,324,342,temp,AbstractLivelinessMonitor.java,132,160
//,3
public class xxx {
    @Override
    public void run() {
      while (!stopped && !Thread.currentThread().isInterrupted()) {
        synchronized (AbstractLivelinessMonitor.this) {
          Iterator<Map.Entry<O, Long>> iterator = running.entrySet().iterator();

          // avoid calculating current time everytime in loop
          long currentTime = clock.getTime();

          while (iterator.hasNext()) {
            Map.Entry<O, Long> entry = iterator.next();
            O key = entry.getKey();
            long interval = getExpireInterval(key);
            if (currentTime > entry.getValue() + interval) {
              iterator.remove();
              expire(key);
              LOG.info("Expired:" + entry.getKey().toString()
                  + " Timed out after " + interval / 1000 + " secs");
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