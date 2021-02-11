//,temp,sample_3875.java,2,9,temp,sample_3873.java,2,9
//,2
public class xxx {
public boolean removeAllLoadBalanacersForNetwork(long networkId, Account caller, long callerUserId) {
List<FirewallRuleVO> rules = _firewallDao.listByNetworkAndPurposeAndNotRevoked(networkId, Purpose.LoadBalancing);
if (rules != null) {


log.info("found lb rules to cleanup");
}
}

};