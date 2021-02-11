//,temp,CiscoVnmcResource.java,507,587,temp,CiscoVnmcResource.java,422,497
//,3
public class xxx {
    private Answer execute(SetStaticNatRulesCommand cmd, int numRetries) {
        String vlanId = cmd.getContextParam(NetworkElementCommand.GUEST_VLAN_TAG);
        String tenant = "vlan-" + vlanId;

        StaticNatRuleTO[] rules = cmd.getRules();
        Map<String, List<StaticNatRuleTO>> publicIpRulesMap = new HashMap<String, List<StaticNatRuleTO>>();
        for (StaticNatRuleTO rule : rules) {
            String publicIp = rule.getSrcIp();
            if (!publicIpRulesMap.containsKey(publicIp)) {
                List<StaticNatRuleTO> publicIpRulesList = new ArrayList<StaticNatRuleTO>();
                publicIpRulesMap.put(publicIp, publicIpRulesList);
            }
            publicIpRulesMap.get(publicIp).add(rule);
        }

        try {
            if (!_connection.createTenantVDCNatPolicySet(tenant)) {
                throw new ExecutionException("Failed to create NAT policy set in VNMC for guest network with vlan " + vlanId);
            }

            if (!_connection.createTenantVDCAclPolicySet(tenant, true)) {
                throw new ExecutionException("Failed to create ACL ingress policy set in VNMC for guest network with vlan " + vlanId);
            }

            if (!_connection.createTenantVDCAclPolicySet(tenant, false)) {
                throw new ExecutionException("Failed to create ACL egress policy set in VNMC for guest network with vlan " + vlanId);
            }

            for (String publicIp : publicIpRulesMap.keySet()) {
                String policyIdentifier = publicIp.replace('.', '-');

                if (!_connection.createTenantVDCDNatPolicy(tenant, policyIdentifier)) {
                    throw new ExecutionException("Failed to create DNAT policy in VNMC for guest network with vlan " + vlanId);
                }
                if (!_connection.createTenantVDCDNatPolicyRef(tenant, policyIdentifier)) {
                    throw new ExecutionException("Failed to associate DNAT policy with NAT policy set in VNMC for guest network with vlan " + vlanId);
                }

                if (!_connection.createTenantVDCAclPolicy(tenant, policyIdentifier)) {
                    throw new ExecutionException("Failed to create ACL policy in VNMC for guest network with vlan " + vlanId);
                }
                if (!_connection.createTenantVDCAclPolicyRef(tenant, policyIdentifier, true)) {
                    throw new ExecutionException("Failed to associate ACL policy with ACL ingress policy set in VNMC for guest network with vlan " + vlanId);
                }
                if (!_connection.createTenantVDCAclPolicyRef(tenant, policyIdentifier, false)) {
                    throw new ExecutionException("Failed to associate ACL policy with ACL egress policy set in VNMC for guest network with vlan " + vlanId);
                }

                for (StaticNatRuleTO rule : publicIpRulesMap.get(publicIp)) {
                    if (rule.revoked()) {
                        if (!_connection.deleteTenantVDCDNatRule(tenant, rule.getId(), policyIdentifier)) {
                            throw new ExecutionException("Failed to delete DNAT rule in VNMC for guest network with vlan " + vlanId);
                        }
                    } else {
                        if (!_connection.createTenantVDCDNatIpPool(tenant, Long.toString(rule.getId()), rule.getDstIp())) {
                            throw new ExecutionException("Failed to create DNAT ip pool in VNMC for guest network with vlan " + vlanId);
                        }

                        if (!_connection.createTenantVDCDNatRule(tenant, rule.getId(), policyIdentifier, rule.getSrcIp())) {
                            throw new ExecutionException("Failed to create DNAT rule in VNMC for guest network with vlan " + vlanId);
                        }
                    }
                }
            }

            if (!_connection.associateAclPolicySet(tenant)) {
                throw new ExecutionException("Failed to associate source NAT policy set with edge security profile in VNMC for guest network with vlan " + vlanId);
            }
        } catch (ExecutionException e) {
            String msg = "SetStaticNatRulesCommand failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd, true, "Success");
    }

};