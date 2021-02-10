//,temp,MetricsSinkAdapter.java,207,218,temp,StandbyCheckpointer.java,145,155
//,3
public class xxx {
  void stop() {
    stopping = true;
    sinkThread.interrupt();
    if (sink instanceof Closeable) {
      IOUtils.cleanupWithLogger(LOG, (Closeable)sink);
    }
    try {
      sinkThread.join();
    } catch (InterruptedException e) {
      LOG.warn("Stop interrupted", e);
    }
  }

};