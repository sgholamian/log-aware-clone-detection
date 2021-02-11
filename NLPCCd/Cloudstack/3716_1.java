//,temp,sample_7801.java,2,17,temp,sample_3014.java,2,17
//,2
public class xxx {
public void dummy_method(){
eventDescription.put("resource", resourceName);
eventDescription.put("id", vo.getUuid());
eventDescription.put("old-state", oldState.name());
eventDescription.put("new-state", newState.name());
String eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new Date());
eventDescription.put("eventDateTime", eventDate);
eventMsg.setDescription(eventDescription);
try {
s_eventBus.publish(eventMsg);
} catch (EventBusException e) {


log.info("failed to publish state change event on the the event bus");
}
}

};