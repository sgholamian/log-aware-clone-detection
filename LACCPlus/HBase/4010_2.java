//,temp,RegionServerFlushTableProcedureManager.java,244,282,temp,RegionServerSnapshotManager.java,316,354
//,2
public class xxx {
    boolean waitForOutstandingTasks() throws ForeignException, InterruptedException {
      LOG.debug("Waiting for local region snapshots to finish.");

      int sz = futures.size();
      try {
        // Using the completion service to process the futures that finish first first.
        for (int i = 0; i < sz; i++) {
          Future<Void> f = taskPool.take();
          f.get();
          if (!futures.remove(f)) {
            LOG.warn("unexpected future" + f);
          }
          LOG.debug("Completed " + (i+1) + "/" + sz +  " local region snapshots.");
        }
        LOG.debug("Completed " + sz +  " local region snapshots.");
        return true;
      } catch (InterruptedException e) {
        LOG.warn("Got InterruptedException in SnapshotSubprocedurePool", e);
        if (!stopped) {
          Thread.currentThread().interrupt();
          throw new ForeignException("SnapshotSubprocedurePool", e);
        }
        // we are stopped so we can just exit.
      } catch (ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof ForeignException) {
          LOG.warn("Rethrowing ForeignException from SnapshotSubprocedurePool", e);
          throw (ForeignException)e.getCause();
        } else if (cause instanceof DroppedSnapshotException) {
          // we have to abort the region server according to contract of flush
          abortable.abort("Received DroppedSnapshotException, aborting", cause);
        }
        LOG.warn("Got Exception in SnapshotSubprocedurePool", e);
        throw new ForeignException(name, e.getCause());
      } finally {
        cancelTasks();
      }
      return false;
    }

};