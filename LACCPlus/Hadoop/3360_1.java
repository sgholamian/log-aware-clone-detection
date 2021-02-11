//,temp,NMClientAsyncImpl.java,384,406,temp,NMClientAsyncImpl.java,360,382
//,2
public class xxx {
  @Override
  public void commitLastReInitializationAsync(ContainerId containerId){
    if (!(callbackHandler instanceof AbstractCallbackHandler)) {
      LOG.error("Callback handler does not implement container commit last " +
          "re-initialization callback methods");
      return;
    }
    AbstractCallbackHandler handler = (AbstractCallbackHandler) callbackHandler;
    if (containers.get(containerId) == null) {
      handler.onCommitLastReInitializationError(
          containerId, RPCUtil.getRemoteException(
              "Container " + containerId + " is not started"));
    }
    try {
      events.put(new ContainerEvent(containerId,
          client.getNodeIdOfStartedContainer(containerId),
          null, ContainerEventType.COMMIT_LAST_REINT));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event Commit re-initialization"
          + " of Container " + containerId);
      handler.onCommitLastReInitializationError(containerId, e);
    }
  }

};