//,temp,NMClientAsyncImpl.java,287,309,temp,NMClientAsyncImpl.java,246,261
//,3
public class xxx {
  @Override
  public void updateContainerResourceAsync(Container container) {
    if (!(callbackHandler instanceof AbstractCallbackHandler)) {
      LOG.error("Callback handler does not implement container resource "
          + "increase callback methods");
      return;
    }
    AbstractCallbackHandler handler = (AbstractCallbackHandler) callbackHandler;
    if (containers.get(container.getId()) == null) {
      handler.onUpdateContainerResourceError(
          container.getId(),
          RPCUtil.getRemoteException(
              "Container " + container.getId() +
                  " is neither started nor scheduled to start"));
    }
    try {
      events.put(new UpdateContainerResourceEvent(container, false));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event of increasing resource of "
          + "Container " + container.getId());
      handler.onUpdateContainerResourceError(container.getId(), e);
    }
  }

};