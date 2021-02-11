//,temp,sample_2278.java,2,12,temp,sample_2279.java,2,13
//,3
public class xxx {
protected void addReservationQueue( String planQueueName, Queue queue, String currResId) {
PlanQueue planQueue = (PlanQueue)queue;
try {
ReservationQueue resQueue = new ReservationQueue(cs, currResId, planQueue);
cs.addQueue(resQueue);
} catch (SchedulerDynamicEditException e) {
} catch (IOException e) {


log.info("exception while trying to activate reservation for plan");
}
}

};