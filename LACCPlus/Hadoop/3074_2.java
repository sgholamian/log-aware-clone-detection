//,temp,FileSystemRMStateStore.java,735,750,temp,LoadJob.java,201,222
//,3
public class xxx {
    @Override
    public void run() {
      LOG.info("Resource usage matcher thread started.");
      try {
        while (progress.getProgress() < 1) {
          // match
          match();
          
          // sleep for some time
          try {
            Thread.sleep(sleepTime);
          } catch (Exception e) {}
        }
        
        // match for progress = 1
        match();
        LOG.info("Resource usage emulation complete! Matcher exiting");
      } catch (Exception e) {
        LOG.info("Exception while running the resource-usage-emulation matcher"
                 + " thread! Exiting.", e);
      }
    }

};