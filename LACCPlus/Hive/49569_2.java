//,temp,LlapTaskSchedulerService.java,1185,1207,temp,LlapTaskSchedulerService.java,1163,1183
//,3
public class xxx {
  @Override
  public void allocateTask(Object task, Resource capability, String[] hosts, String[] racks,
      Priority priority, Object containerSignature, Object clientCookie) {
    TezTaskAttemptID id = getTaskAttemptId(task);
    TaskInfo taskInfo = new TaskInfo(localityDelayConf, clock, task, clientCookie, priority,
        capability, hosts, racks, clock.getTime(), id);
    LOG.info("Received allocateRequest. task={}, priority={}, capability={}, hosts={}",
        task, priority, capability, Arrays.toString(hosts));
    writeLock.lock();
    try {
      if (!dagRunning && metrics != null && id != null) {
        metrics.setDagId(id.getTaskID().getVertexID().getDAGId().toString());
      }
      dagRunning = true;
      dagStats.registerTaskRequest(hosts, racks);
    } finally {
      writeLock.unlock();
    }
    addPendingTask(taskInfo);
    trySchedulingPendingTasks();
  }

};