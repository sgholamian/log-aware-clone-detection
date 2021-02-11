//,temp,sample_6004.java,2,18,temp,sample_5928.java,2,18
//,2
public class xxx {
public void dummy_method(){
final String routerType = router.getType().toString();
final UserStatisticsVO previousStats = _userStatsDao.findBy(router.getAccountId(), router.getDataCenterId(), network.getId(), forVpc ? routerNic.getIPv4Address() : null, router.getId(), routerType);
NetworkUsageAnswer answer = null;
try {
answer = (NetworkUsageAnswer) _agentMgr.easySend(router.getHostId(), usageCmd);
} catch (final Exception e) {
continue;
}
if (answer != null) {
if (!answer.getResult()) {


log.info("error while collecting network stats from router from host details");
}
}
}

};