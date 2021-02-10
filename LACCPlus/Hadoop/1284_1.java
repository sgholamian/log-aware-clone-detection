//,temp,PlanQueue.java,128,144,temp,JobControl.java,273,290
//,3
public class xxx {
  synchronized void removeChildQueue(CSQueue remQueue)
      throws SchedulerDynamicEditException {
    if (remQueue.getCapacity() > 0) {
      throw new SchedulerDynamicEditException("Queue " + remQueue
          + " being removed has non zero capacity.");
    }
    Iterator<CSQueue> qiter = childQueues.iterator();
    while (qiter.hasNext()) {
      CSQueue cs = qiter.next();
      if (cs.equals(remQueue)) {
        qiter.remove();
        if (LOG.isDebugEnabled()) {
          LOG.debug("Removed child queue: {}", cs.getQueueName());
        }
      }
    }
  }

};