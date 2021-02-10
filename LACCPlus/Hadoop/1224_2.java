//,temp,TestMiniMRChildTask.java,375,393,temp,ResourceManager.java,596,614
//,3
public class xxx {
    @Override
    protected void serviceStop() throws Exception {

      DefaultMetricsSystem.shutdown();
      if (pauseMonitor != null) {
        pauseMonitor.stop();
      }

      if (rmContext != null) {
        RMStateStore store = rmContext.getStateStore();
        try {
          store.close();
        } catch (Exception e) {
          LOG.error("Error closing store.", e);
        }
      }

      super.serviceStop();
    }

};