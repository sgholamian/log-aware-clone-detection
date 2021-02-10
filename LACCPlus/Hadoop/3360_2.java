//,temp,NMClientAsyncImpl.java,384,406,temp,NMClientAsyncImpl.java,360,382
//,2
public class xxx {
  @Override
  public void rollbackLastReInitializationAsync(ContainerId containerId){
    if (!(callbackHandler instanceof AbstractCallbackHandler)) {
      LOG.error("Callback handler does not implement container rollback "
          + "callback methods");
      return;
    }
    AbstractCallbackHandler handler = (AbstractCallbackHandler) callbackHandler;
    if (containers.get(containerId) == null) {
      handler.onRollbackLastReInitializationError(
          containerId, RPCUtil.getRemoteException(
              "Container " + containerId + " is not started"));
    }
    try {
      events.put(new ContainerEvent(containerId,
          client.getNodeIdOfStartedContainer(containerId),
          null, ContainerEventType.ROLLBACK_LAST_REINIT));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event Rollback re-initialization"
          + " of Container " + containerId);
      handler.onRollbackLastReInitializationError(containerId, e);
    }
  }

};