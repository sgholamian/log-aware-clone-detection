//,temp,NMClientAsyncImpl.java,462,474,temp,NMClientAsyncImpl.java,398,410
//,3
public class xxx {
      private ContainerState onExceptionRaised(StatefulContainer container,
          ContainerEvent event, Throwable t) {
        try {
          container.nmClientAsync.getCallbackHandler().onStartContainerError(
              event.getContainerId(), t);
        } catch (Throwable thr) {
          // Don't process user created unchecked exception
          LOG.info(
              "Unchecked exception is thrown from onStartContainerError for " +
                  "Container " + event.getContainerId(), thr);
        }
        return ContainerState.FAILED;
      }

};