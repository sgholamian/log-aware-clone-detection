//,temp,NMClientAsyncImpl.java,417,440,temp,NMClientAsyncImpl.java,367,396
//,3
public class xxx {
      @Override
      public ContainerState transition(
          StatefulContainer container, ContainerEvent event) {
        ContainerId containerId = event.getContainerId();
        try {
          StartContainerEvent scEvent = null;
          if (event instanceof StartContainerEvent) {
            scEvent = (StartContainerEvent) event;
          }
          assert scEvent != null;
          Map<String, ByteBuffer> allServiceResponse =
              container.nmClientAsync.getClient().startContainer(
                  scEvent.getContainer(), scEvent.getContainerLaunchContext());
          try {
            container.nmClientAsync.getCallbackHandler().onContainerStarted(
                containerId, allServiceResponse);
          } catch (Throwable thr) {
            // Don't process user created unchecked exception
            LOG.info("Unchecked exception is thrown from onContainerStarted for "
                + "Container " + containerId, thr);
          }
          return ContainerState.RUNNING;
        } catch (YarnException e) {
          return onExceptionRaised(container, event, e);
        } catch (IOException e) {
          return onExceptionRaised(container, event, e);
        } catch (Throwable t) {
          return onExceptionRaised(container, event, t);
        }
      }

};