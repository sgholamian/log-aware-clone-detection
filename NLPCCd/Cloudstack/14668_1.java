//,temp,sample_2564.java,2,18,temp,sample_2561.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (s_logger.isDebugEnabled()) {
new Request(-1l, -1l, cmds, true, false).logD("Startup request from directly connected host: ", true);
}
if (old) {
final StartupCommand firstCmd = cmds[0];
host = findHostByGuid(firstCmd.getGuid());
if (host == null) {
host = findHostByGuid(firstCmd.getGuidWithoutResource());
}
if (host != null && host.getRemoved() == null) {


log.info("found the host by guid old host reconnected as new");
}
}
}

};