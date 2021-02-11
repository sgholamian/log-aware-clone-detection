//,temp,MessageBusBase.java,315,339,temp,SynchronizationEvent.java,50,66
//,3
public class xxx {
    public boolean waitEvent() throws InterruptedException {
        synchronized (this) {
            if (signalled)
                return true;

            while (true) {
                try {
                    wait();
                    assert (signalled);
                    return signalled;
                } catch (InterruptedException e) {
                    s_logger.debug("unexpected awaken signal in wait()");
                    throw e;
                }
            }
        }
    }

};