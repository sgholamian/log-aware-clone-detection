//,temp,sample_8803.java,2,12,temp,sample_8793.java,2,12
//,3
public class xxx {
private void createVPNUserEvent(UsageEventVO event) {
long zoneId = 0L;
long userId = event.getResourceId();
if (EventTypes.EVENT_VPN_USER_ADD.equals(event.getType())) {
if (s_logger.isDebugEnabled()) {


log.info("creating vpn user for account");
}
}
}

};