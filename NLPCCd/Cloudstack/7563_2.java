//,temp,sample_6918.java,2,14,temp,sample_6882.java,2,14
//,2
public class xxx {
public void onAgentDisconnect(long agentId, com.cloud.host.Status state) {
if (state == com.cloud.host.Status.Alert || state == com.cloud.host.Status.Disconnected) {
HostVO host = _hostDao.findById(agentId);
if (host.getType() == Type.ConsoleProxy) {
String name = host.getName();
if (s_logger.isInfoEnabled()) {


log.info("console proxy agent disconnected proxy");
}
}
}
}

};