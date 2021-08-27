//,temp,HiveClientCache.java,523,534,temp,HiveClientCache.java,425,431
//,3
public class xxx {
    @Override
    protected void finalize() throws Throwable {
      if (users.get() != 0) {
        LOG.warn("Closing client with non-zero user count: users=" + users.get() + " expired=" + expiredFromCache);
      }

      try {
        this.tearDown();
      } finally {
        super.finalize();
      }
    }

};