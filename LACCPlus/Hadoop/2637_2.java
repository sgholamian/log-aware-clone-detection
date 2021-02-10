//,temp,DistCp.java,454,465,temp,FederationRegistryClient.java,266,277
//,3
public class xxx {
      @Override
      public Boolean run() {
        try {
          registryImpl.delete(key, recursive);
          return true;
        } catch (Throwable e) {
          if (throwIfFails) {
            LOG.error("Registry remove key " + key + " failed", e);
          }
        }
        return false;
      }

};