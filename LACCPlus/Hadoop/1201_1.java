//,temp,CommitterEventHandler.java,139,154,temp,ResourceManager.java,675,707
//,3
public class xxx {
      @Override
      public void run() {
        CommitterEvent event = null;
        while (!stopped.get() && !Thread.currentThread().isInterrupted()) {
          try {
            event = eventQueue.take();
          } catch (InterruptedException e) {
            if (!stopped.get()) {
              LOG.error("Returning, interrupted : " + e);
            }
            return;
          }
          // the events from the queue are handled in parallel
          // using a thread pool
          launcherPool.execute(new EventProcessor(event));        }
      }

};