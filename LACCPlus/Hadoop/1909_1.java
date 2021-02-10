//,temp,FSLeafQueue.java,262,292,temp,FSParentQueue.java,150,180
//,3
public class xxx {
  @Override
  public void updateDemand() {
    // Compute demand by iterating through apps in the queue
    // Limit demand to maxResources
    Resource maxRes = scheduler.getAllocationConfiguration()
        .getMaxResources(getName());
    demand = Resources.createResource(0);
    readLock.lock();
    try {
      for (FSAppAttempt sched : runnableApps) {
        if (Resources.equals(demand, maxRes)) {
          break;
        }
        updateDemandForApp(sched, maxRes);
      }
      for (FSAppAttempt sched : nonRunnableApps) {
        if (Resources.equals(demand, maxRes)) {
          break;
        }
        updateDemandForApp(sched, maxRes);
      }
    } finally {
      readLock.unlock();
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("The updated demand for " + getName() + " is " + demand
          + "; the max is " + maxRes);
      LOG.debug("The updated fairshare for " + getName() + " is "
          + getFairShare());
    }
  }

};