//,temp,OpenFileCtx.java,386,421,temp,OpenFileCtx.java,276,310
//,3
public class xxx {
    @Override
    public void run() {
      while (activeState && enabledDump) {
        try {
          if (nonSequentialWriteInMemory.get() >= DUMP_WRITE_WATER_MARK) {
            dump();
          }
          synchronized (OpenFileCtx.this) {
            if (nonSequentialWriteInMemory.get() < DUMP_WRITE_WATER_MARK) {
              OpenFileCtx.this.notifyAll();
              try {
                OpenFileCtx.this.wait();
                if (LOG.isDebugEnabled()) {
                  LOG.debug("Dumper woke up");
                }
              } catch (InterruptedException e) {
                LOG.info("Dumper is interrupted, dumpFilePath= "
                    + OpenFileCtx.this.dumpFilePath);
              }
            }
          }
          if (LOG.isDebugEnabled()) {
            LOG.debug("Dumper checking OpenFileCtx activeState: " + activeState
                + " enabledDump: " + enabledDump);
          }
        } catch (Throwable t) {
          // unblock threads with new request
          synchronized (OpenFileCtx.this) {
            OpenFileCtx.this.notifyAll();
          }
          LOG.info("Dumper get Throwable: " + t + ". dumpFilePath: "
              + OpenFileCtx.this.dumpFilePath, t);
          activeState = false;
        }
      }
    }

};