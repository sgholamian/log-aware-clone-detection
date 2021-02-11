//,temp,NMClientAsyncImpl.java,336,358,temp,NMClientAsyncImpl.java,311,334
//,3
public class xxx {
  @Override
  public void reInitializeContainerAsync(ContainerId containerId,
      ContainerLaunchContext containerLaunchContex, boolean autoCommit){
    if (!(callbackHandler instanceof AbstractCallbackHandler)) {
      LOG.error("Callback handler does not implement container re-initialize "
          + "callback methods");
      return;
    }
    AbstractCallbackHandler handler = (AbstractCallbackHandler) callbackHandler;
    if (containers.get(containerId) == null) {
      handler.onContainerReInitializeError(
          containerId, RPCUtil.getRemoteException(
              "Container " + containerId + " is not started"));
    }
    try {
      events.put(new ReInitializeContainerEvevnt(containerId,
          client.getNodeIdOfStartedContainer(containerId),
          containerLaunchContex, autoCommit));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event of re-initializing of "
          + "Container " + containerId);
      handler.onContainerReInitializeError(containerId, e);
    }
  }

};