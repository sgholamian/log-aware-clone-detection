//,temp,AbstractDelegationTokenSecretManager.java,617,633,temp,AllocationFileLoaderService.java,152,164
//,3
public class xxx {
  public void stopThreads() {
    if (LOG.isDebugEnabled())
      LOG.debug("Stopping expired delegation token remover thread");
    running = false;
    
    if (tokenRemoverThread != null) {
      synchronized (noInterruptsLock) {
        tokenRemoverThread.interrupt();
      }
      try {
        tokenRemoverThread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(
            "Unable to join on token removal thread", e);
      }
    }
  }

};