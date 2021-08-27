//,temp,sample_7088.java,2,12,temp,sample_7087.java,2,11
//,3
public class xxx {
public void notify(EventObject eventObject) throws Exception {
String message = eventObject.toString();
Level level = determineLevel(eventObject);
MessagePayload payload = new MessagePayload(getHostName(), level, getServiceName(), message);
if (log.isInfoEnabled()) {


log.info("sending notification to nagios");
}
}

};