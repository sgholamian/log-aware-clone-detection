//,temp,LocalDistributedCacheManager.java,250,260,temp,DataStreamer.java,982,993
//,3
public class xxx {
        @Override
        public Void run() {
          try {
            classLoaderCreated.close();
            classLoaderCreated = null;
          } catch (IOException e) {
            LOG.warn("Failed to close classloader created " +
                "by LocalDistributedCacheManager");
          }
          return null;
        }

};