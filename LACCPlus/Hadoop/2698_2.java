//,temp,AbstractJavaKeyStoreProvider.java,161,167,temp,DeleteCompletionCallback.java,40,48
//,3
public class xxx {
  @Override
  public void processResult(CuratorFramework client,
      CuratorEvent event) throws
      Exception {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Delete event {}", event);
    }
    events.incrementAndGet();
  }

};