//,temp,RMStateStore.java,1188,1206,temp,ContainerLauncherImpl.java,410,419
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  private boolean notifyStoreOperationFailedInternal(
      Exception failureCause) {
    boolean isFenced = false;
    LOG.error("State store operation failed ", failureCause);

    if (HAUtil.isHAEnabled(getConfig())) {
      rmDispatcher.getEventHandler().handle(
          new RMFatalEvent(RMFatalEventType.STATE_STORE_FENCED,
              failureCause));
      isFenced = true;
    } else {
      rmDispatcher.getEventHandler().handle(
          new RMFatalEvent(RMFatalEventType.STATE_STORE_OP_FAILED,
              failureCause));
    }

    return isFenced;
  }

};