//,temp,EditLogTailer.java,156,165,temp,AbstractDelegationTokenSecretManager.java,617,633
//,3
public class xxx {
  public void stop() throws IOException {
    tailerThread.setShouldRun(false);
    tailerThread.interrupt();
    try {
      tailerThread.join();
    } catch (InterruptedException e) {
      LOG.warn("Edit log tailer thread exited with an exception");
      throw new IOException(e);
    }
  }

};