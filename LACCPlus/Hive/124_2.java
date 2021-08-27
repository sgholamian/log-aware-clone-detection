//,temp,TaskExecutorService.java,1045,1059,temp,TaskExecutorService.java,1028,1043
//,3
public class xxx {
    @Override
    public void onSuccess(TaskRunner2Result result) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Received successful completion for: {}",
            taskWrapper.getRequestId());
      }
      updateFallOffStats(taskWrapper.getRequestId());
      knownTasks.remove(taskWrapper.getRequestId());
      taskWrapper.setIsInPreemptableQueue(false);
      taskWrapper.maybeUnregisterForFinishedStateNotifications();
      taskWrapper.getTaskRunnerCallable().setWmCountersDone();
      metrics.addMetricsQueueTime(taskWrapper.getTaskRunnerCallable().getQueueTime());
      metrics.addMetricsRunningTime(taskWrapper.getTaskRunnerCallable().getRunningTime());
      updatePreemptionListAndNotify(result.getEndReason());
      taskWrapper.getTaskRunnerCallable().getCallback().onSuccess(result);
    }

};