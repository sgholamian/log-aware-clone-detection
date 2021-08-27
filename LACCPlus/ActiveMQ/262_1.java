//,temp,LockableServiceSupport.java,132,155,temp,LeaseLockerIOExceptionHandler.java,39,65
//,3
public class xxx {
    protected void keepLockAlive() {
        boolean stop = false;
        try {
            Locker locker = getLocker();
            if (locker != null) {
                if (!locker.keepAlive()) {
                    stop = true;
                }
            }
        } catch (SuppressReplyException e) {
            if (stopOnError) {
                stop = true;
            }
            LOG.warn("locker keepAlive resulted in", e);
        } catch (IOException e) {
            if (stopOnError) {
                stop = true;
            }
            LOG.warn("locker keepAlive resulted in", e);
        }
        if (stop) {
            stopBroker();
        }
    }

};