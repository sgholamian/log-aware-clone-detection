//,temp,LlapTaskSchedulerService.java,2422,2444,temp,LlapTaskSchedulerService.java,2325,2345
//,3
public class xxx {
    @Override
    public Void call() {
      while (!isShutdown.get() && !Thread.currentThread().isInterrupted()) {
        try {
          TaskInfo taskInfo = highPriorityTaskQueue.take();
          // Tasks can exist in the queue even after they have been scheduled.
          // Process task Preemption only if the task is still in PENDING state.
          processTaskPreemption(taskInfo);

        } catch (InterruptedException e) {
          if (isShutdown.get()) {
            LOG.info("PreemptTaskScheduler thread interrupted after shutdown");
            break;
          } else {
            LOG.warn("PreemptTaskScheduler thread interrupted before being shutdown");
            throw new RuntimeException("PreemptTaskScheduler thread interrupted without being shutdown", e);
          }
        }
      }
      return null;
    }

};