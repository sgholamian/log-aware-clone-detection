//,temp,StorageContainerManager.java,486,509,temp,KeyProviderCache.java,50,68
//,3
public class xxx {
  public KeyProviderCache(long expiryMs) {
    cache = CacheBuilder.newBuilder()
        .expireAfterAccess(expiryMs, TimeUnit.MILLISECONDS)
        .removalListener(new RemovalListener<URI, KeyProvider>() {
          @Override
          public void onRemoval(
              @Nonnull RemovalNotification<URI, KeyProvider> notification) {
            try {
              assert notification.getValue() != null;
              notification.getValue().close();
            } catch (Throwable e) {
              LOG.error(
                  "Error closing KeyProvider with uri ["
                      + notification.getKey() + "]", e);
            }
          }
        })
        .build();
  }

};