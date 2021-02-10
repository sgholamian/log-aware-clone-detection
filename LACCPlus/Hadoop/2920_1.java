//,temp,CapacitySchedulerPlanFollower.java,111,131,temp,CapacitySchedulerPlanFollower.java,92,109
//,3
public class xxx {
  @Override
  protected void createDefaultReservationQueue(
      String planQueueName, Queue queue, String defReservationId) {
    PlanQueue planQueue = (PlanQueue)queue;
    if (cs.getQueue(defReservationId) == null) {
      try {
        ReservationQueue defQueue =
            new ReservationQueue(cs, defReservationId, planQueue);
        cs.addQueue(defQueue);
      } catch (SchedulerDynamicEditException e) {
        LOG.warn(
            "Exception while trying to create default reservation queue for plan: {}",
            planQueueName, e);
      } catch (IOException e) {
        LOG.warn(
            "Exception while trying to create default reservation queue for " +
                "plan: {}",
            planQueueName, e);
      }
    }
  }

};