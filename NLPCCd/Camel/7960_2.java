//,temp,sample_2495.java,2,12,temp,sample_2496.java,2,16
//,3
public class xxx {
public void handleNotification(Notification notification, Object handback) {
JMXConnectionNotification connectionNotification = (JMXConnectionNotification)notification;
if (!connectionNotification.getConnectionId().equals(mConnectionId)) {
return;
}
if (connectionNotification.getType().equals(JMXConnectionNotification.NOTIFS_LOST) || connectionNotification.getType().equals(JMXConnectionNotification.CLOSED) || connectionNotification.getType().equals(JMXConnectionNotification.FAILED)) {
if (mJmxEndpoint.getReconnectOnConnectionFailure()) {
scheduleReconnect();
} else {


log.info("the jmx consumer will not be reconnected use reconnectonconnectionfailure to enable reconnections");
}
}
}

};