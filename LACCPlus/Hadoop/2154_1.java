//,temp,ZKFailoverController.java,445,478,temp,ZKFailoverController_before_log_fix.java,444,477
//,2
public class xxx {
  private ActiveAttemptRecord waitForActiveAttempt(int timeoutMillis)
      throws InterruptedException {
    long st = System.nanoTime();
    long waitUntil = st + TimeUnit.NANOSECONDS.convert(
        timeoutMillis, TimeUnit.MILLISECONDS);
    
    do {
      // periodically check health state, because entering an
      // unhealthy state could prevent us from ever attempting to
      // become active. We can detect this and respond to the user
      // immediately.
      synchronized (this) {
        if (lastHealthState != State.SERVICE_HEALTHY) {
          // early out if service became unhealthy
          return null;
        }
      }

      synchronized (activeAttemptRecordLock) {
        if ((lastActiveAttemptRecord != null &&
            lastActiveAttemptRecord.nanoTime >= st)) {
          return lastActiveAttemptRecord;
        }
        // Only wait 1sec so that we periodically recheck the health state
        // above.
        activeAttemptRecordLock.wait(1000);
      }
    } while (System.nanoTime() < waitUntil);
    
    // Timeout elapsed.
    LOG.warn(timeoutMillis + "ms timeout elapsed waiting for an attempt " +
        "to become active");
    return null;
  }

};