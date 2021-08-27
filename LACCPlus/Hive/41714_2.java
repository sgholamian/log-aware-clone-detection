//,temp,HiveClientCache.java,437,443,temp,HiveClientCache.java,415,420
//,3
public class xxx {
    public synchronized void acquire() {
      users.incrementAndGet();
      if (users.get() > 1) {
        LOG.warn("Unexpected increment of user count beyond one: " + users.get() + " " + this);
      }
    }

};