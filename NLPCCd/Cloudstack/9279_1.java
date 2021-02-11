//,temp,sample_2320.java,2,13,temp,sample_2319.java,2,11
//,3
public class xxx {
public boolean processAnswers(long agentId, long seq, Answer[] answers) {
HostVO host = _hostDao.findById(agentId);
if (host != null) {
if ((host.getManagementServerId() == null) || (mgmtSrvrId != host.getManagementServerId())) {
return false;
}
} else {


log.info("agent not found not collecting direct network usage from trafficmonitor");
}
}

};