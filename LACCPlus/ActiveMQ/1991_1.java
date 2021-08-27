//,temp,PooledTaskRunner.java,93,108,temp,DedicatedTaskRunner.java,73,87
//,3
public class xxx {
    @Override
    public void shutdown(long timeout) throws InterruptedException {
        LOG.trace("Shutdown timeout: {} task: {}", timeout, task);
        synchronized (runable) {
            shutdown = true;
            // the check on the thread is done
            // because a call to iterate can result in
            // shutDown() being called, which would wait forever
            // waiting for iterating to finish
            if (runningThread != Thread.currentThread()) {
                if (iterating) {
                    runable.wait(timeout);
                }
            }
        }
    }

};