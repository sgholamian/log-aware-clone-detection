//,temp,VpcVirtualNetworkApplianceManagerImpl.java,589,607,temp,VpcVirtualNetworkApplianceManagerImpl.java,479,496
//,3
public class xxx {
    @Override
    protected void finalizeNetworkRulesForNetwork(final Commands cmds, final DomainRouterVO domainRouterVO, final Provider provider, final Long guestNetworkId) {

        super.finalizeNetworkRulesForNetwork(cmds, domainRouterVO, provider, guestNetworkId);

        if (domainRouterVO.getVpcId() != null) {

            if (domainRouterVO.getState() == State.Starting || domainRouterVO.getState() == State.Running) {
                if (_networkModel.isProviderSupportServiceInNetwork(guestNetworkId, Service.NetworkACL, Provider.VPCVirtualRouter)) {
                    final List<NetworkACLItemVO> networkACLs = _networkACLMgr.listNetworkACLItems(guestNetworkId);
                    if (networkACLs != null && !networkACLs.isEmpty()) {
                        s_logger.debug("Found " + networkACLs.size() + " network ACLs to apply as a part of VPC VR " + domainRouterVO + " start for guest network id=" + guestNetworkId);
                        _commandSetupHelper.createNetworkACLsCommands(networkACLs, domainRouterVO, cmds, guestNetworkId, false);
                    }
                }
            }
        }
    }

};