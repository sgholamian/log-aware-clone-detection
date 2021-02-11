//,temp,ContainerExecutor.java,801,815,temp,KeyProviderCache.java,70,86
//,3
public class xxx {
  public KeyProvider get(final Configuration conf,
      final URI serverProviderUri) {
    if (serverProviderUri == null) {
      return null;
    }
    try {
      return cache.get(serverProviderUri, new Callable<KeyProvider>() {
        @Override
        public KeyProvider call() throws Exception {
          return KMSUtil.createKeyProviderFromUri(conf, serverProviderUri);
        }
      });
    } catch (Exception e) {
      LOG.error("Could not create KeyProvider for DFSClient !!", e);
      return null;
    }
  }

};