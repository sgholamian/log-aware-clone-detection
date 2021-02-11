//,temp,LibvirtSecurityGroupRulesCommandWrapper.java,41,68,temp,OvmResourceBase.java,925,947
//,3
public class xxx {
    @Override
    public Answer execute(final SecurityGroupRulesCmd command, final LibvirtComputingResource libvirtComputingResource) {
        String vif = null;
        String brname = null;
        try {
            final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();

            final Connect conn = libvirtUtilitiesHelper.getConnectionByVmName(command.getVmName());
            final List<InterfaceDef> nics = libvirtComputingResource.getInterfaces(conn, command.getVmName());

            vif = nics.get(0).getDevName();
            brname = nics.get(0).getBrName();
        } catch (final LibvirtException e) {
            return new SecurityGroupRuleAnswer(command, false, e.toString());
        }

        final boolean result = libvirtComputingResource.addNetworkRules(command.getVmName(), Long.toString(command.getVmId()), command.getGuestIp(), command.getGuestIp6(), command.getSignature(),
                Long.toString(command.getSeqNum()), command.getGuestMac(), command.stringifyRules(), vif, brname, command.getSecIpsString());

        if (!result) {
            s_logger.warn("Failed to program network rules for vm " + command.getVmName());
            return new SecurityGroupRuleAnswer(command, false, "programming network rules failed");
        } else {
            s_logger.debug("Programmed network rules for vm " + command.getVmName() + " guestIp=" + command.getGuestIp() + ",ingress numrules="
                    + command.getIngressRuleSet().size() + ",egress numrules=" + command.getEgressRuleSet().size());
            return new SecurityGroupRuleAnswer(command);
        }
    }

};