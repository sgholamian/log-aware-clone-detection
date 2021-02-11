//,temp,CiscoVnmcResource.java,507,587,temp,CiscoVnmcResource.java,422,497
//,3
public class xxx {
    private Answer execute(SetPortForwardingRulesCommand cmd, int numRetries) {
        String vlanId = cmd.getContextParam(NetworkElementCommand.GUEST_VLAN_TAG);
        String tenant = "vlan-" + vlanId;

        PortForwardingRuleTO[] rules = cmd.getRules();
        Map<String, List<PortForwardingRuleTO>> publicIpRulesMap = new HashMap<String, List<PortForwardingRuleTO>>();
        for (PortForwardingRuleTO rule : rules) {
            String publicIp = rule.getSrcIp();
            if (!publicIpRulesMap.containsKey(publicIp)) {
                List<PortForwardingRuleTO> publicIpRulesList = new ArrayList<PortForwardingRuleTO>();
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

                if (!_connection.createTenantVDCPFPolicy(tenant, policyIdentifier)) {
                    throw new ExecutionException("Failed to create PF policy in VNMC for guest network with vlan " + vlanId);
                }
                if (!_connection.createTenantVDCPFPolicyRef(tenant, policyIdentifier)) {
                    throw new ExecutionException("Failed to associate PF policy with NAT policy set in VNMC for guest network with vlan " + vlanId);
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

                for (PortForwardingRuleTO rule : publicIpRulesMap.get(publicIp)) {
                    if (rule.revoked()) {
                        if (!_connection.deleteTenantVDCPFRule(tenant, rule.getId(), policyIdentifier)) {
                            throw new ExecutionException("Failed to delete PF rule in VNMC for guest network with vlan " + vlanId);
                        }
                    } else {
                        if (!_connection.createTenantVDCPFIpPool(tenant, Long.toString(rule.getId()), rule.getDstIp())) {
                            throw new ExecutionException("Failed to create PF ip pool in VNMC for guest network with vlan " + vlanId);
                        }
                        if (!_connection.createTenantVDCPFPortPool(tenant, Long.toString(rule.getId()), Integer.toString(rule.getDstPortRange()[0]),
                            Integer.toString(rule.getDstPortRange()[1]))) {
                            throw new ExecutionException("Failed to create PF port pool in VNMC for guest network with vlan " + vlanId);
                        }

                        if (!_connection.createTenantVDCPFRule(tenant, rule.getId(), policyIdentifier, rule.getProtocol().toUpperCase(), rule.getSrcIp(),
                            Integer.toString(rule.getSrcPortRange()[0]), Integer.toString(rule.getSrcPortRange()[1]))) {
                            throw new ExecutionException("Failed to create PF rule in VNMC for guest network with vlan " + vlanId);
                        }
                    }
                }
            }

            if (!_connection.associateAclPolicySet(tenant)) {
                throw new ExecutionException("Failed to associate source NAT policy set with edge security profile in VNMC for guest network with vlan " + vlanId);
            }
        } catch (ExecutionException e) {
            String msg = "SetPortForwardingRulesCommand failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }

        return new Answer(cmd, true, "Success");
    }

};