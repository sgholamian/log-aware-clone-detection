//,temp,NMClientAsyncImpl.java,417,440,temp,NMClientAsyncImpl.java,367,396
//,3
public class xxx {
      @Override
      public ContainerState transition(
          StatefulContainer container, ContainerEvent event) {
        ContainerId containerId = event.getContainerId();
        try {
         container.nmClientAsync.getClient().stopContainer(
              containerId, event.getNodeId());
         try {
            container.nmClientAsync.getCallbackHandler().onContainerStopped(
                event.getContainerId());
          } catch (Throwable thr) {
            // Don't process user created unchecked exception
            LOG.info("Unchecked exception is thrown from onContainerStopped for "
                + "Container " + event.getContainerId(), thr);
          }
          return ContainerState.DONE;
        } catch (YarnException e) {
          return onExceptionRaised(container, event, e);
        } catch (IOException e) {
          return onExceptionRaised(container, event, e);
        } catch (Throwable t) {
          return onExceptionRaised(container, event, t);
        }
      }

};