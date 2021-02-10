//,temp,FairScheduler.java,1573,1590,temp,DomainSocketWatcher.java,266,284
//,3
public class xxx {
  @Override
  public void close() throws IOException {
    lock.lock();
    try {
      if (closed) return;
      if (LOG.isDebugEnabled()) {
        LOG.debug(this + ": closing");
      }
      closed = true;
    } finally {
      lock.unlock();
    }
    // Close notificationSockets[0], so that notificationSockets[1] gets an EOF
    // event.  This will wake up the thread immediately if it is blocked inside
    // the select() system call.
    notificationSockets[0].close();
    // Wait for the select thread to terminate.
    Uninterruptibles.joinUninterruptibly(watcherThread);
  }

};