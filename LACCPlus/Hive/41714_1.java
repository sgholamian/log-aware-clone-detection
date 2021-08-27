//,temp,HiveClientCache.java,437,443,temp,HiveClientCache.java,415,420
//,3
public class xxx {
    public synchronized void setExpiredFromCache() {
      if (users.get() != 0) {
        LOG.warn("Evicted client has non-zero user count: " + users.get());
      }

      expiredFromCache = true;
    }

};