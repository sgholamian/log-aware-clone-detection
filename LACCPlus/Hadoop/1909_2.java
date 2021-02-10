//,temp,FSLeafQueue.java,262,292,temp,FSParentQueue.java,150,180
//,3
public class xxx {
  @Override
  public void updateDemand() {
    // Compute demand by iterating through apps in the queue
    // Limit demand to maxResources
    Resource maxRes = scheduler.getAllocationConfiguration()
        .getMaxResources(getName());
    writeLock.lock();
    try {
      demand = Resources.createResource(0);
      for (FSQueue childQueue : childQueues) {
        childQueue.updateDemand();
        Resource toAdd = childQueue.getDemand();
        if (LOG.isDebugEnabled()) {
          LOG.debug("Counting resource from " + childQueue.getName() + " " +
              toAdd + "; Total resource consumption for " + getName() +
              " now " + demand);
        }
        demand = Resources.add(demand, toAdd);
        demand = Resources.componentwiseMin(demand, maxRes);
        if (Resources.equals(demand, maxRes)) {
          break;
        }
      }
    } finally {
      writeLock.unlock();
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("The updated demand for " + getName() + " is " + demand +
          "; the max is " + maxRes);
    }    
  }

};