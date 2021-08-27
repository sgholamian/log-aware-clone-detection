//,temp,DummyTxnManager.java,316,326,temp,TxnHandler.java,4332,4338
//,3
public class xxx {
  @Override
  protected void destruct() {
    if (lockMgr != null) {
      try {
        lockMgr.close();
      } catch (LockException e) {
        // Not much I can do about it.
        LOG.warn("Got exception when closing lock manager " + e.getMessage());
      }
    }
  }

};