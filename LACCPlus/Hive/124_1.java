//,temp,TaskExecutorService.java,1045,1059,temp,TaskExecutorService.java,1028,1043
//,3
public class xxx {
    @Override
    public void onFailure(Throwable t) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Received failed completion for: {}",
            taskWrapper.getRequestId());
      }
      updateFallOffStats(taskWrapper.getRequestId());
      knownTasks.remove(taskWrapper.getRequestId());
      taskWrapper.setIsInPreemptableQueue(false);
      taskWrapper.maybeUnregisterForFinishedStateNotifications();
      taskWrapper.getTaskRunnerCallable().setWmCountersDone();
      updatePreemptionListAndNotify(null);
      taskWrapper.getTaskRunnerCallable().getCallback().onFailure(t);
      LOG.error("Failed notification received: Stacktrace: " + ExceptionUtils.getStackTrace(t));
    }

};