//,temp,CapacitySchedulerPlanFollower.java,111,131,temp,CapacitySchedulerPlanFollower.java,92,109
//,3
public class xxx {
  @Override
  protected void addReservationQueue(
      String planQueueName, Queue queue, String currResId) {
    PlanQueue planQueue = (PlanQueue)queue;
    try {
      ReservationQueue resQueue =
          new ReservationQueue(cs, currResId, planQueue);
      cs.addQueue(resQueue);
    } catch (SchedulerDynamicEditException e) {
      LOG.warn(
          "Exception while trying to activate reservation: {} for plan: {}",
          currResId, planQueueName, e);
    } catch (IOException e) {
      LOG.warn(
          "Exception while trying to activate reservation: {} for plan: {}",
          currResId, planQueueName, e);
    }
  }

};