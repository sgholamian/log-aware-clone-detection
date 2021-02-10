//,temp,MetricsSinkAdapter.java,209,221,temp,ApplicationMasterLauncher.java,97,106
//,3
public class xxx {
  void stop() {
    stopping = true;
    sinkThread.interrupt();
    if (sink instanceof Closeable) {
      IOUtils.cleanup(LOG, (Closeable)sink);
    }
    try {
      sinkThread.join();
    }
    catch (InterruptedException e) {
      LOG.warn("Stop interrupted", e);
    }
  }

};