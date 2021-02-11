//,temp,NMClientAsyncImpl.java,493,505,temp,RMContainerImpl.java,405,427
//,3
public class xxx {
    @Override
    public void handle(ContainerEvent event) {
      writeLock.lock();
      try {
        try {
          this.stateMachine.doTransition(event.getType(), event);
        } catch (InvalidStateTransitionException e) {
          LOG.error("Can't handle this event at current state", e);
        }
      } finally {
        writeLock.unlock();
      }
    }

};