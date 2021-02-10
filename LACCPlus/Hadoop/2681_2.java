//,temp,MetricsSinkAdapter.java,207,218,temp,EditLogTailer.java,236,246
//,3
public class xxx {
  public void stop() throws IOException {
    rollEditsRpcExecutor.shutdown();
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