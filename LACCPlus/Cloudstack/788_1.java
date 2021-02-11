//,temp,SynchronizationEvent.java,68,82,temp,SynchronizationEvent.java,50,66
//,3
public class xxx {
    public boolean waitEvent(long timeOutMiliseconds) throws InterruptedException {
        synchronized (this) {
            if (signalled)
                return true;

            try {
                wait(timeOutMiliseconds);
                return signalled;
            } catch (InterruptedException e) {
                // TODO, we don't honor time out semantics when the waiting thread is interrupted
                s_logger.debug("unexpected awaken signal in wait(...)");
                throw e;
            }
        }
    }

};