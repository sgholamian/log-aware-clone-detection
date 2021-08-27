//,temp,MetastoreTaskThreadAlwaysTestImpl.java,50,59,temp,AcidOpenTxnsCounterService.java,46,61
//,3
public class xxx {
  @Override
  public void run() {
    try {
      long start = System.currentTimeMillis();
      isAliveCounter++;
      txnHandler.countOpenTxns();
      long now = System.currentTimeMillis();
      if (now - lastLogTime > LOG_INTERVAL_MS) {
        LOG.info("Open txn counter ran for {} seconds. isAliveCounter: {}", (now - start) / 1000, isAliveCounter);
        lastLogTime = now;
      }
    }
    catch (Throwable t) {
      LOG.error("Unexpected error in thread: {}, message: {}", Thread.currentThread().getName(), t.getMessage(), t);
    }
  }

};