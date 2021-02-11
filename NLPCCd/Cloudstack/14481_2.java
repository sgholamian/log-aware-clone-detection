//,temp,sample_3759.java,2,9,temp,sample_3773.java,2,9
//,2
public class xxx {
protected void updateSecondaryHost(final HostVO host, final StartupStorageCommand startup, final Host.Type type) throws AgentAuthnException {
String zoneToken = startup.getDataCenter();
if (zoneToken == null) {


log.info("no zone token passed in cannot not find zone for the agent");
}
}

};