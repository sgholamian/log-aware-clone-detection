//,temp,sample_4623.java,2,10,temp,sample_4625.java,2,10
//,2
public class xxx {
public NotificationEventResponse get_next_notification(NotificationEventRequest rqst) throws TException {
try {
authorizeProxyPrivilege();
} catch (Exception ex) {


log.info("not authorized to make the get next notification call you can try to disable");
}
}

};