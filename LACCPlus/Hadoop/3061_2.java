//,temp,NMClientAsyncImpl.java,287,309,temp,NMClientAsyncImpl.java,246,261
//,3
public class xxx {
  public void startContainerAsync(
      Container container, ContainerLaunchContext containerLaunchContext) {
    if (containers.putIfAbsent(container.getId(),
        new StatefulContainer(this, container.getId())) != null) {
      callbackHandler.onStartContainerError(container.getId(),
          RPCUtil.getRemoteException("Container " + container.getId() +
              " is already started or scheduled to start"));
    }
    try {
      events.put(new StartContainerEvent(container, containerLaunchContext));
    } catch (InterruptedException e) {
      LOG.warn("Exception when scheduling the event of starting Container " +
          container.getId());
      callbackHandler.onStartContainerError(container.getId(), e);
    }
  }

};