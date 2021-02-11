//,temp,DockerContainerDeletionTask.java,53,62,temp,DeletionService.java,85,95
//,3
public class xxx {
  public void delete(DeletionTask deletionTask) {
    if (debugDelay != -1) {
      if (LOG.isDebugEnabled()) {
        String msg = String.format("Scheduling DeletionTask (delay %d) : %s",
            debugDelay, deletionTask.toString());
        LOG.debug(msg);
      }
      recordDeletionTaskInStateStore(deletionTask);
      sched.schedule(deletionTask, debugDelay, TimeUnit.SECONDS);
    }
  }

};