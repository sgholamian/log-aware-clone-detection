//,temp,VpcVirtualNetworkApplianceManagerImpl.java,589,607,temp,VpcVirtualNetworkApplianceManagerImpl.java,479,496
//,3
public class xxx {
    @Override
    protected void finalizeIpAssocForNetwork(final Commands cmds, final VirtualRouter domainRouterVO, final Provider provider, final Long guestNetworkId,
            final Map<String, String> vlanMacAddress) {

        if (domainRouterVO.getVpcId() == null) {
            super.finalizeIpAssocForNetwork(cmds, domainRouterVO, provider, guestNetworkId, vlanMacAddress);
            return;
        }

        if (domainRouterVO.getState() == State.Starting || domainRouterVO.getState() == State.Running) {
            final ArrayList<? extends PublicIpAddress> publicIps = getPublicIpsToApply(domainRouterVO, provider, guestNetworkId, IpAddress.State.Releasing);

            if (publicIps != null && !publicIps.isEmpty()) {
                s_logger.debug("Found " + publicIps.size() + " ip(s) to apply as a part of domR " + domainRouterVO + " start.");
                // Re-apply public ip addresses - should come before PF/LB/VPN
                _commandSetupHelper.createVpcAssociatePublicIPCommands(domainRouterVO, publicIps, cmds, vlanMacAddress);
            }
        }
    }

};