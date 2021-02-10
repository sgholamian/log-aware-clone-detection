//,temp,NMClientAsyncImpl.java,336,358,temp,NMClientAsyncImpl.java,311,334
//,3
public class xxx {
  @Override
  public void restartContainerAsync(ContainerId containerId){
    if (!(callbackHandler instanceof AbstractCallbackHandler)) {
      LOG.error("Callback handler does not implement container restart "
          + "callback methods");
      return;
    }
    AbstractCallbackHandler handler = (AbstractCallbackHandler) callbackHandler;
    if (containers.get(containerId) == null) {
      handler.onContainerRestartError(
          containerId, RPCUtil.getRemoteException(
              "Container " + containerId + " is not started"));
    }
    try {
      events.put(new ContainerEvent(containerId,
          client.getNodeIdOfStartedContainer(containerId),
          null, ContainerEventType.RESTART_CONTAINER));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event of restart of "
          + "Container " + containerId);
      handler.onContainerRestartError(containerId, e);
    }
  }

};