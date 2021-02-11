//,temp,JobHistoryEventHandler.java,1691,1702,temp,WriteCtx.java,105,117
//,3
public class xxx {
    void flush() throws IOException {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Flushing " + toString());
      }
      synchronized (lock) {
        if (numUnflushedCompletionEvents != 0) { // skipped timer cancel.
          writer.flush();
          numUnflushedCompletionEvents = 0;
          resetFlushTimer();
        }
      }
    }

};