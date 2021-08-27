//,temp,sample_4623.java,2,10,temp,sample_4625.java,2,10
//,2
public class xxx {
public NotificationEventsCountResponse get_notification_events_count(NotificationEventsCountRequest rqst) throws TException {
try {
authorizeProxyPrivilege();
} catch (Exception ex) {


log.info("not authorized to make the get notification events count call you can try to disable");
}
}

};