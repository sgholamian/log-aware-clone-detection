//,temp,TimeoutExceptionInjector.java,118,128,temp,TimeoutExceptionInjector.java,81,94
//,3
public class xxx {
  public void complete() {
    synchronized (this.timerTask) {
      if (this.complete) {
        LOG.warn("Timer already marked completed, ignoring!");
        return;
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug("Marking timer as complete - no error notifications will be received for " +
          "this timer.");
      }
      this.complete = true;
    }
    this.timer.cancel();
  }

};