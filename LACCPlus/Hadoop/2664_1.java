//,temp,TestBlockScanner.java,953,964,temp,TestBlockScanner.java,853,866
//,3
public class xxx {
      @Override
      public Boolean get() {
        synchronized (info) {
          if (info.blocksScanned >= numExpectedBlocks) {
            LOG.info("info = {}.  blockScanned has now reached 1.", info);
            return true;
          } else {
            LOG.info("info = {}.  Waiting for blockScanned to reach 1.", info);
            return false;
          }
        }
      }

};