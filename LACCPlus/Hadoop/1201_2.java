//,temp,CommitterEventHandler.java,139,154,temp,ResourceManager.java,675,707
//,3
public class xxx {
      @Override
      public void run() {

        SchedulerEvent event;

        while (!stopped && !Thread.currentThread().isInterrupted()) {
          try {
            event = eventQueue.take();
          } catch (InterruptedException e) {
            LOG.error("Returning, interrupted : " + e);
            return; // TODO: Kill RM.
          }

          try {
            scheduler.handle(event);
          } catch (Throwable t) {
            // An error occurred, but we are shutting down anyway.
            // If it was an InterruptedException, the very act of 
            // shutdown could have caused it and is probably harmless.
            if (stopped) {
              LOG.warn("Exception during shutdown: ", t);
              break;
            }
            LOG.fatal("Error in handling event type " + event.getType()
                + " to the scheduler", t);
            if (shouldExitOnError
                && !ShutdownHookManager.get().isShutdownInProgress()) {
              LOG.info("Exiting, bbye..");
              System.exit(-1);
            }
          }
        }
      }

};