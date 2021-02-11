//,temp,sample_3874.java,2,14,temp,sample_3876.java,2,14
//,2
public class xxx {
public boolean removeAllLoadBalanacersForIp(long ipId, Account caller, long callerUserId) {
List<FirewallRuleVO> rules = _firewallDao.listByIpAndPurposeAndNotRevoked(ipId, Purpose.LoadBalancing);
if (rules != null) {
for (FirewallRule rule : rules) {
boolean result = deleteLoadBalancerRule(rule.getId(), true, caller, callerUserId, false);
if (result == false) {


log.info("unable to remove load balancer rule");
}
}
}
}

};