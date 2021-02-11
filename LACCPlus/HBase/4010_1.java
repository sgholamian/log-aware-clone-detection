//,temp,RegionServerFlushTableProcedureManager.java,244,282,temp,RegionServerSnapshotManager.java,316,354
//,2
public class xxx {
    boolean waitForOutstandingTasks() throws ForeignException, InterruptedException {
      LOG.debug("Waiting for local region flush to finish.");

      int sz = futures.size();
      try {
        // Using the completion service to process the futures.
        for (int i = 0; i < sz; i++) {
          Future<Void> f = taskPool.take();
          f.get();
          if (!futures.remove(f)) {
            LOG.warn("unexpected future" + f);
          }
          LOG.debug("Completed " + (i+1) + "/" + sz +  " local region flush tasks.");
        }
        LOG.debug("Completed " + sz +  " local region flush tasks.");
        return true;
      } catch (InterruptedException e) {
        LOG.warn("Got InterruptedException in FlushSubprocedurePool", e);
        if (!stopped) {
          Thread.currentThread().interrupt();
          throw new ForeignException("FlushSubprocedurePool", e);
        }
        // we are stopped so we can just exit.
      } catch (ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof ForeignException) {
          LOG.warn("Rethrowing ForeignException from FlushSubprocedurePool", e);
          throw (ForeignException)e.getCause();
        } else if (cause instanceof DroppedSnapshotException) {
          // we have to abort the region server according to contract of flush
          abortable.abort("Received DroppedSnapshotException, aborting", cause);
        }
        LOG.warn("Got Exception in FlushSubprocedurePool", e);
        throw new ForeignException(name, e.getCause());
      } finally {
        cancelTasks();
      }
      return false;
    }

};