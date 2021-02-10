//,temp,MiniYARNCluster.java,563,584,temp,TestResourceManagerAdministrationProtocolPBClientImpl.java,70,102
//,3
public class xxx {
    protected synchronized void serviceStart() throws Exception {
      try {
        new Thread() {
          public void run() {
            nodeManagers[index].start();
          }
        }.start();
        int waitCount = 0;
        while (nodeManagers[index].getServiceState() == STATE.INITED
            && waitCount++ < 60) {
          LOG.info("Waiting for NM " + index + " to start...");
          Thread.sleep(1000);
        }
        if (nodeManagers[index].getServiceState() != STATE.STARTED) {
          // RM could have failed.
          throw new IOException("NodeManager " + index + " failed to start");
        }
        super.serviceStart();
      } catch (Throwable t) {
        throw new YarnRuntimeException(t);
      }
    }

};