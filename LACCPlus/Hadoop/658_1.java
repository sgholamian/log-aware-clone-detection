//,temp,SchedulingMonitor.java,88,102,temp,FairScheduler.java,293,304
//,3
public class xxx {
    @Override
    public void run() {
      while (!stopped && !Thread.currentThread().isInterrupted()) {
        //invoke the preemption policy at a regular pace
        //the policy will generate preemption or kill events
        //managed by the dispatcher
        invokePolicy();
        try {
          Thread.sleep(monitorInterval);
        } catch (InterruptedException e) {
          LOG.info(getName() + " thread interrupted");
          break;
        }
      }
    }

};