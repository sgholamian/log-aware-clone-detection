//,temp,sample_5493.java,2,11,temp,sample_1342.java,2,12
//,3
public class xxx {
public LocalizerHeartbeatResponse processHeartbeat(LocalizerStatus status) {
String locId = status.getLocalizerId();
synchronized (privLocalizers) {
LocalizerRunner localizer = privLocalizers.get(locId);
if (null == localizer) {


log.info("unknown localizer with localizerid is sending heartbeat ordering it to die");
}
}
}

};