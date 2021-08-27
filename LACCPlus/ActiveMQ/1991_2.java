//,temp,PooledTaskRunner.java,93,108,temp,DedicatedTaskRunner.java,73,87
//,3
public class xxx {
    @Override
    public void shutdown(long timeout) throws InterruptedException {
        LOG.trace("Shutdown timeout: {} task: {}", timeout, task);
        synchronized (mutex) {
            shutdown = true;
            pending = true;
            mutex.notifyAll();

            // Wait till the thread stops ( no need to wait if shutdown
            // is called from thread that is shutting down)
            if (Thread.currentThread() != thread && !threadTerminated) {
                mutex.wait(timeout);
            }
        }
    }

};