//,temp,MetricsSinkAdapter.java,207,218,temp,StandbyCheckpointer.java,145,155
//,3
public class xxx {
  public void stop() throws IOException {
    cancelAndPreventCheckpoints("Stopping checkpointer");
    thread.setShouldRun(false);
    thread.interrupt();
    try {
      thread.join();
    } catch (InterruptedException e) {
      LOG.warn("Edit log tailer thread exited with an exception");
      throw new IOException(e);
    }
  }

};