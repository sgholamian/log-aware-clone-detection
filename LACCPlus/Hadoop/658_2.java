//,temp,SchedulingMonitor.java,88,102,temp,FairScheduler.java,293,304
//,3
public class xxx {
    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          continuousSchedulingAttempt();
          Thread.sleep(getContinuousSchedulingSleepMs());
        } catch (InterruptedException e) {
          LOG.warn("Continuous scheduling thread interrupted. Exiting.", e);
          return;
        }
      }
    }

};