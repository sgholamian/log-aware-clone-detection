//,temp,JobHistoryEventHandler.java,1691,1702,temp,WriteCtx.java,105,117
//,3
public class xxx {
  public void trimWrite(int delta) {
    Preconditions.checkState(delta < count);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Trim write request by delta:" + delta + " " + toString());
    }
    synchronized(this) {
      trimDelta = delta;
      if (originalCount == INVALID_ORIGINAL_COUNT) {
        originalCount = count;
      }
      trimData();
    }
  }

};