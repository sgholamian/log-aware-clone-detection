//,temp,StorageContainerManager.java,486,509,temp,KeyProviderCache.java,50,68
//,3
public class xxx {
  private void initContainerReportCache(OzoneConfiguration conf) {
    containerReportCache =
        CacheBuilder.newBuilder()
            .expireAfterAccess(Long.MAX_VALUE, TimeUnit.MILLISECONDS)
            .maximumSize(Integer.MAX_VALUE)
            .removalListener(
                new RemovalListener<String, ContainerStat>() {
                  @Override
                  public void onRemoval(
                      RemovalNotification<String, ContainerStat>
                          removalNotification) {
                    synchronized (containerReportCache) {
                      ContainerStat stat = removalNotification.getValue();
                      // remove invalid container report
                      metrics.decrContainerStat(stat);
                      LOG.debug(
                          "Remove expired container stat entry for datanode: " +
                              "{}.",
                          removalNotification.getKey());
                    }
                  }
                })
            .build();
  }

};