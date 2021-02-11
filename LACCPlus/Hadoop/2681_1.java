//,temp,MetricsSinkAdapter.java,207,218,temp,EditLogTailer.java,236,246
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