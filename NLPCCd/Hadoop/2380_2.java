//,temp,sample_2279.java,2,13,temp,sample_2280.java,2,14
//,3
public class xxx {
protected void createDefaultReservationQueue( String planQueueName, Queue queue, String defReservationId) {
PlanQueue planQueue = (PlanQueue)queue;
if (cs.getQueue(defReservationId) == null) {
try {
ReservationQueue defQueue = new ReservationQueue(cs, defReservationId, planQueue);
cs.addQueue(defQueue);
} catch (SchedulerDynamicEditException e) {


log.info("exception while trying to create default reservation queue for plan");
}
}
}

};