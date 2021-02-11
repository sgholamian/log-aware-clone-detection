//,temp,sample_3875.java,2,9,temp,sample_3873.java,2,9
//,2
public class xxx {
public boolean removeAllLoadBalanacersForIp(long ipId, Account caller, long callerUserId) {
List<FirewallRuleVO> rules = _firewallDao.listByIpAndPurposeAndNotRevoked(ipId, Purpose.LoadBalancing);
if (rules != null) {


log.info("found lb rules to cleanup");
}
}

};