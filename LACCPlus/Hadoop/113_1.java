//,temp,MiniYARNCluster.java,709,737,temp,TestResourceManagerAdministrationProtocolPBClientImpl.java,70,102
//,3
public class xxx {
    @Override
    protected synchronized void serviceStart() throws Exception {
      try {
        new Thread() {
          public void run() {
            appHistoryServer.start();
          };
        }.start();
        int waitCount = 0;
        while (appHistoryServer.getServiceState() == STATE.INITED
            && waitCount++ < 60) {
          LOG.info("Waiting for Timeline Server to start...");
          Thread.sleep(1500);
        }
        if (appHistoryServer.getServiceState() != STATE.STARTED) {
          // AHS could have failed.
          throw new IOException(
              "ApplicationHistoryServer failed to start. Final state is "
                  + appHistoryServer.getServiceState());
        }
        super.serviceStart();
      } catch (Throwable t) {
        throw new YarnRuntimeException(t);
      }
      LOG.info("MiniYARN ApplicationHistoryServer address: "
          + getConfig().get(YarnConfiguration.TIMELINE_SERVICE_ADDRESS));
      LOG.info("MiniYARN ApplicationHistoryServer web address: "
          + getConfig().get(YarnConfiguration.TIMELINE_SERVICE_WEBAPP_ADDRESS));
    }

};