//,temp,RegistryAdminService.java,510,519,temp,SwiftNativeFileSystem.java,156,162
//,3
public class xxx {
    @Override
    public Integer call() throws Exception {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Executing {}", this);
      }
      return purge(path,
          selector,
          purgePolicy,
          callback);
    }

};