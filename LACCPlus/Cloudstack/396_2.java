//,temp,LibvirtSecurityGroupRulesCommandWrapper.java,41,68,temp,OvmResourceBase.java,925,947
//,3
public class xxx {
    private Answer execute(SecurityGroupRulesCmd cmd) {
        boolean result = false;
        try {
            OvmVif.Details vif = getVifFromVm(cmd.getVmName(), null);
            String vifDeviceName = vif.name;
            String bridgeName = vif.bridge;
            result =
                addNetworkRules(cmd.getVmName(), Long.toString(cmd.getVmId()), cmd.getGuestIp(), cmd.getSignature(), String.valueOf(cmd.getSeqNum()), cmd.getGuestMac(),
                    cmd.stringifyRules(), vifDeviceName, bridgeName);
        } catch (XmlRpcException e) {
            s_logger.error(e);
            result = false;
        }

        if (!result) {
            s_logger.warn("Failed to program network rules for vm " + cmd.getVmName());
            return new SecurityGroupRuleAnswer(cmd, false, "programming network rules failed");
        } else {
            s_logger.info("Programmed network rules for vm " + cmd.getVmName() + " guestIp=" + cmd.getGuestIp() + ":ingress num rules=" + cmd.getIngressRuleSet().size() +
                ":egress num rules=" + cmd.getEgressRuleSet().size());
            return new SecurityGroupRuleAnswer(cmd);
        }
    }

};