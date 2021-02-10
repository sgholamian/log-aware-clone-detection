//,temp,NMClientAsyncImpl.java,462,474,temp,NMClientAsyncImpl.java,442,453
//,3
public class xxx {
      @Override
      public void transition(StatefulContainer container, ContainerEvent event) {
        try {
          container.nmClientAsync.getCallbackHandler().onStartContainerError(
              event.getContainerId(),
              RPCUtil.getRemoteException(STOP_BEFORE_START_ERROR_MSG));
        } catch (Throwable thr) {
          // Don't process user created unchecked exception
          LOG.info(
              "Unchecked exception is thrown from onStartContainerError for " +
                  "Container " + event.getContainerId(), thr);
        }
      }

};