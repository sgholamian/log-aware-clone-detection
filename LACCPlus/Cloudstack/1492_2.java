//,temp,LoadBalancingRulesManagerImpl.java,2018,2032,temp,LoadBalancingRulesManagerImpl.java,1999,2016
//,2
public class xxx {
    @Override
    public boolean removeAllLoadBalanacersForIp(long ipId, Account caller, long callerUserId) {

        //Included revoked rules to remove the rules of ips which are in revoke state
        List<FirewallRuleVO> rules = _firewallDao.listByIpAndPurpose(ipId, Purpose.LoadBalancing);

        if (rules != null) {
            s_logger.debug("Found " + rules.size() + " lb rules to cleanup");
            for (FirewallRule rule : rules) {
                boolean result = deleteLoadBalancerRule(rule.getId(), true, caller, callerUserId, false);
                if (result == false) {
                    s_logger.warn("Unable to remove load balancer rule " + rule.getId());
                    return false;
                }
            }
        }
        return true;
    }

};