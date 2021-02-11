//,temp,HttpServer2.java,475,494,temp,HttpServer.java,320,339
//,2
public class xxx {
    @Override
    public boolean isRunning() {
      if (super.isRunning()) {
        return true;
      }
      // We might be hitting JETTY-1316. If the internal state changed from
      // STARTING to STARTED in the middle of the check, the above call may
      // return false.  Check it one more time.
      LOG.warn("HttpServer Acceptor: isRunning is false. Rechecking.");
      try {
        Thread.sleep(10);
      } catch (InterruptedException ie) {
        // Mark this thread as interrupted. Someone up in the call chain
        // might care.
        Thread.currentThread().interrupt();
      }
      boolean runState = super.isRunning();
      LOG.warn("HttpServer Acceptor: isRunning is " + runState);
      return runState;
    }

};