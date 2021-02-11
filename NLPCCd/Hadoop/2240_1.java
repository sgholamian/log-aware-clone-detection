//,temp,sample_2266.java,2,17,temp,sample_1760.java,2,17
//,3
public class xxx {
public void dummy_method(){
entity.setDomainId(domainId);
entity.addPrimaryFilter(USER_TIMELINE_FILTER_NAME, ugi.getShortUserName());
TimelineEvent event = new TimelineEvent();
event.setEventType(appEvent.toString());
event.setTimestamp(System.currentTimeMillis());
entity.addEvent(event);
try {
TimelinePutResponse response = timelineClient.putEntities(entity);
processTimelineResponseErrors(response);
} catch (YarnException | IOException | ClientHandlerException e) {


log.info("app attempt start end event could not be published for");
}
}

};