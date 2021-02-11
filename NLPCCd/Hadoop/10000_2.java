//,temp,sample_2280.java,2,14,temp,sample_2281.java,2,15
//,3
public class xxx {
protected void createDefaultReservationQueue( String planQueueName, Queue queue, String defReservationId) {
PlanQueue planQueue = (PlanQueue)queue;
if (cs.getQueue(defReservationId) == null) {
try {
ReservationQueue defQueue = new ReservationQueue(cs, defReservationId, planQueue);
cs.addQueue(defQueue);
} catch (SchedulerDynamicEditException e) {
} catch (IOException e) {


log.info("exception while trying to create default reservation queue for plan");
}
}
}

};