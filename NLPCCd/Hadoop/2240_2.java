//,temp,sample_2266.java,2,17,temp,sample_1760.java,2,17
//,3
public class xxx {
public void dummy_method(){
TimelineEvent event = new TimelineEvent();
event.setTimestamp(System.currentTimeMillis());
event.setEventType("foo_event");
entity.addEvent(event);
UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
long startWrite = System.nanoTime();
try {
tlc.putEntities(entity);
} catch (Exception e) {
context.getCounter(PerfCounters.TIMELINE_SERVICE_WRITE_FAILURES). increment(1);


log.info("writing to the timeline service failed");
}
}

};