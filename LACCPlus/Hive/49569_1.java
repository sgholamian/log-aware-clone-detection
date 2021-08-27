//,temp,LlapTaskSchedulerService.java,1185,1207,temp,LlapTaskSchedulerService.java,1163,1183
//,3
public class xxx {
  @Override
  public void allocateTask(Object task, Resource capability, ContainerId containerId,
      Priority priority, Object containerSignature, Object clientCookie) {
    // Container affinity can be implemented as Host affinity for LLAP. Not required until
    // 1:1 edges are used in Hive.
    TezTaskAttemptID id = getTaskAttemptId(task);
    TaskInfo taskInfo = new TaskInfo(localityDelayConf, clock, task, clientCookie, priority,
        capability, null, null, clock.getTime(), id);
    LOG.info("Received allocateRequest. task={}, priority={}, capability={}, containerId={}",
        task, priority, capability, containerId);
    writeLock.lock();
    try {
      if (!dagRunning && metrics != null && id != null) {
        metrics.setDagId(id.getTaskID().getVertexID().getDAGId().toString());
      }
      dagRunning = true;
      dagStats.registerTaskRequest(null, null);
    } finally {
      writeLock.unlock();
    }
    addPendingTask(taskInfo);
    trySchedulingPendingTasks();
  }

};