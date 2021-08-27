//,temp,WorkloadManager.java,1696,1717,temp,WorkloadManager.java,1501,1513
//,3
public class xxx {
  Future<Boolean> applyMoveSessionAsync(WmTezSession srcSession, String destPoolName) {
    currentLock.lock();
    MoveSession moveSession;
    try {
      moveSession = new MoveSession(srcSession, destPoolName);
      current.moveSessions.add(moveSession);
      LOG.info("Queued move session: {}", moveSession);
      notifyWmThreadUnderLock();
    } finally {
      currentLock.unlock();
    }
    return moveSession.future;
  }

};