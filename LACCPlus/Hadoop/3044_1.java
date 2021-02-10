//,temp,AbstractLauncher.java,191,201,temp,NativeAzureFileSystemHelper.java,121,130
//,3
public class xxx {
  private void dumpLocalResources() {
    if (log.isDebugEnabled()) {
      log.debug("{} resources: ", localResources.size());
      for (Map.Entry<String, LocalResource> entry : localResources.entrySet()) {

        String key = entry.getKey();
        LocalResource val = entry.getValue();
        log.debug(key + "=" + ServiceUtils.stringify(val.getResource()));
      }
    }
  }

};