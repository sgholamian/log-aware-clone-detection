//,temp,LlapTaskSchedulerService.java,2422,2444,temp,LlapTaskSchedulerService.java,2325,2345
//,3
public class xxx {
    @Override
    public Void call() {
      while (!isShutdown.get() && !Thread.currentThread().isInterrupted()) {
        try {
          TaskInfo taskInfo = getNextTask();
          taskInfo.setInDelayedQueue(false);
          // Tasks can exist in the delayed queue even after they have been scheduled.
          // Trigger scheduling only if the task is still in PENDING state.
          processEvictedTask(taskInfo);

        } catch (InterruptedException e) {
          if (isShutdown.get()) {
            LOG.info("DelayedTaskScheduler thread interrupted after shutdown");
            break;
          } else {
            LOG.warn("DelayedTaskScheduler thread interrupted before being shutdown");
            throw new RuntimeException(
                "DelayedTaskScheduler thread interrupted without being shutdown", e);
          }
        }
      }
      return null;
    }

};