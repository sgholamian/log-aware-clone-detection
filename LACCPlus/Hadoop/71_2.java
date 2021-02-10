//,temp,OpenFileCtx.java,386,421,temp,OpenFileCtx.java,276,310
//,3
public class xxx {
  private void waitForDump() {
    if (!enabledDump) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Do nothing, dump is disabled.");
      }
      return;
    }

    if (nonSequentialWriteInMemory.get() < DUMP_WRITE_WATER_MARK) {
      return;
    }

    // wake up the dumper thread to dump the data
    synchronized (this) {
      if (nonSequentialWriteInMemory.get() >= DUMP_WRITE_WATER_MARK) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Asking dumper to dump...");
        }
        if (dumpThread == null) {
          dumpThread = new Daemon(new Dumper());
          dumpThread.start();
        } else {
          this.notifyAll();          
        }
      }
      
      while (nonSequentialWriteInMemory.get() >= DUMP_WRITE_WATER_MARK) {
        try {
          this.wait();
        } catch (InterruptedException ignored) {
        }
      }

    }
  }

};