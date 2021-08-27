//,temp,WorkloadManager.java,1696,1717,temp,WorkloadManager.java,1501,1513
//,3
public class xxx {
  @VisibleForTesting
  /**
   * Adds a test event that's processed at the end of WM iteration.
   * This allows tests to wait for an iteration to finish without messing with the threading
   * logic (that is prone to races if we e.g. remember the state before and wait for it to change,
   * self-deadlocking when triggering things explicitly and calling a blocking API, and hanging
   * forever if we wait for "another iteration"). If addTestEvent is called after all the other
   * calls of interest, it is guaranteed that the events from those calls will be processed
   * fully when the future is triggered.
   */
  Future<Boolean> addTestEvent() {
    SettableFuture<Boolean> testEvent = SettableFuture.create();
    currentLock.lock();
    try {
      LOG.info("Adding test event " + System.identityHashCode(testEvent));
      current.testEvents.add(testEvent);
      notifyWmThreadUnderLock();
    } finally {
      currentLock.unlock();
    }
    return testEvent;
  }

};