//,temp,ProcedureExecutor.java,2071,2088,temp,RootProcedureState.java,145,155
//,3
public class xxx {
    private void checkThreadCount(final int stuckCount) {
      // nothing to do if there are no runnable tasks
      if (stuckCount < 1 || !scheduler.hasRunnables()) {
        return;
      }

      // add a new thread if the worker stuck percentage exceed the threshold limit
      // and every handler is active.
      final float stuckPerc = ((float) stuckCount) / workerThreads.size();
      // let's add new worker thread more aggressively, as they will timeout finally if there is no
      // work to do.
      if (stuckPerc >= addWorkerStuckPercentage && workerThreads.size() < maxPoolSize) {
        final KeepAliveWorkerThread worker = new KeepAliveWorkerThread(threadGroup);
        workerThreads.add(worker);
        worker.start();
        LOG.debug("Added new worker thread {}", worker);
      }
    }

};