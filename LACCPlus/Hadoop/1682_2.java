//,temp,RMCommunicator.java,253,271,temp,AsyncDispatcher.java,136,162
//,3
public class xxx {
  @Override
  protected void serviceStop() throws Exception {
    if (drainEventsOnStop) {
      blockNewEvents = true;
      LOG.info("AsyncDispatcher is draining to stop, igonring any new events.");
      synchronized (waitForDrained) {
        while (!drained && eventHandlingThread != null
            && eventHandlingThread.isAlive()) {
          waitForDrained.wait(1000);
          LOG.info("Waiting for AsyncDispatcher to drain. Thread state is :" +
              eventHandlingThread.getState());
        }
      }
    }
    stopped = true;
    if (eventHandlingThread != null) {
      eventHandlingThread.interrupt();
      try {
        eventHandlingThread.join();
      } catch (InterruptedException ie) {
        LOG.warn("Interrupted Exception while stopping", ie);
      }
    }

    // stop all the components
    super.serviceStop();
  }

};