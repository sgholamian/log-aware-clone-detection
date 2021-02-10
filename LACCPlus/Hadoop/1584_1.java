//,temp,TaskHeartbeatHandler.java,132,163,temp,AbstractLivelinessMonitor.java,112,139
//,3
public class xxx {
    @Override
    public void run() {
      while (!stopped && !Thread.currentThread().isInterrupted()) {
        Iterator<Map.Entry<TaskAttemptId, ReportTime>> iterator =
            runningAttempts.entrySet().iterator();

        // avoid calculating current time everytime in loop
        long currentTime = clock.getTime();

        while (iterator.hasNext()) {
          Map.Entry<TaskAttemptId, ReportTime> entry = iterator.next();
          boolean taskTimedOut = (taskTimeOut > 0) && 
              (currentTime > (entry.getValue().getLastProgress() + taskTimeOut));
           
          if(taskTimedOut) {
            // task is lost, remove from the list and raise lost event
            iterator.remove();
            eventHandler.handle(new TaskAttemptDiagnosticsUpdateEvent(entry
                .getKey(), "AttemptID:" + entry.getKey().toString()
                + " Timed out after " + taskTimeOut / 1000 + " secs"));
            eventHandler.handle(new TaskAttemptEvent(entry.getKey(),
                TaskAttemptEventType.TA_TIMED_OUT));
          }
        }
        try {
          Thread.sleep(taskTimeOutCheckInterval);
        } catch (InterruptedException e) {
          LOG.info("TaskHeartbeatHandler thread interrupted");
          break;
        }
      }
    }

};