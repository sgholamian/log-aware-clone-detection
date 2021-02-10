//,temp,NMClientAsyncImpl.java,493,505,temp,RMNodeImpl.java,446,467
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