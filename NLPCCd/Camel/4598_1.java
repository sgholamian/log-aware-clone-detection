//,temp,sample_3413.java,2,12,temp,sample_3412.java,2,11
//,2
public class xxx {
public boolean resumeOrStartConsumer(Consumer consumer) throws Exception {
if (consumer instanceof Suspendable) {
boolean resumed = ServiceHelper.resumeService(consumer);
if (resumed) {
} else {


log.info("consumer already resumed");
}
}
}

};