//,temp,FileSystemRMStateStore.java,735,750,temp,LoadJob.java,246,264
//,3
public class xxx {
    @Override
    public void run() {
      LOG.info("Status reporter thread started.");
      try {
        while (!isInterrupted() && progress.getProgress() < 1) {
          // report progress
          context.progress();

          // sleep for some time
          try {
            Thread.sleep(100); // sleep for 100ms
          } catch (Exception e) {}
        }
        
        LOG.info("Status reporter thread exiting");
      } catch (Exception e) {
        LOG.info("Exception while running the status reporter thread!", e);
      }
    }

};