//,temp,LockableServiceSupport.java,132,155,temp,LeaseLockerIOExceptionHandler.java,39,65
//,3
public class xxx {
    @Override
    protected boolean hasLockOwnership() throws IOException {
        boolean hasLock = true;

        if (broker.getPersistenceAdapter() instanceof LockableServiceSupport) {
            Locker locker = ((LockableServiceSupport) broker.getPersistenceAdapter()).getLocker();

            if (locker != null) {
                try {
                    if (!locker.keepAlive()) {
                        hasLock = false;
                    }
                }
                catch (SuppressReplyException ignoreWhileHandlingInProgress) {
                }
                catch (IOException ignored) {
                }

                if (!hasLock) {
                    LOG.warn("Lock keepAlive failed, no longer lock owner with: {}", locker);
                    throw new IOException("Lock keepAlive failed, no longer lock owner with: " + locker);
                }
            }
        }

        return hasLock;
    }

};