//,temp,HiveClientCache.java,523,534,temp,HiveClientCache.java,425,431
//,3
public class xxx {
    private void release() {
      if (users.get() > 0) {
        users.decrementAndGet();
      } else {
        LOG.warn("Unexpected attempt to decrement user count of zero: " + users.get() + " " + this);
      }
    }

};