//,temp,NetworkACLManagerImpl.java,87,125,temp,VpcManagerImpl.java,1919,1951
//,3
public class xxx {
    @Override
    public boolean applyNetworkACL(final long aclId) throws ResourceUnavailableException {
        boolean handled = true;
        boolean aclApplyStatus = true;

        final List<NetworkACLItemVO> rules = _networkACLItemDao.listByACL(aclId);
        //Find all networks using this ACL and apply the ACL
        final List<NetworkVO> networks = _networkDao.listByAclId(aclId);
        for (final NetworkVO network : networks) {
            if (!applyACLItemsToNetwork(network.getId(), rules)) {
                handled = false;
                break;
            }
        }

        final List<VpcGatewayVO> vpcGateways = _vpcGatewayDao.listByAclIdAndType(aclId, VpcGateway.Type.Private);
        for (final VpcGatewayVO vpcGateway : vpcGateways) {
            final PrivateGateway privateGateway = _vpcSvc.getVpcPrivateGateway(vpcGateway.getId());

            if (!applyACLToPrivateGw(privateGateway)) {
                aclApplyStatus = false;
                s_logger.debug("failed to apply network acl item on private gateway " + privateGateway.getId() + "acl id " + aclId);
                break;
            }
        }

        if (handled && aclApplyStatus) {
            for (final NetworkACLItem rule : rules) {
                if (rule.getState() == NetworkACLItem.State.Revoke) {
                    removeRule(rule);
                } else if (rule.getState() == NetworkACLItem.State.Add) {
                    final NetworkACLItemVO ruleVO = _networkACLItemDao.findById(rule.getId());
                    ruleVO.setState(NetworkACLItem.State.Active);
                    _networkACLItemDao.update(ruleVO.getId(), ruleVO);
                }
            }
        }
        return handled && aclApplyStatus;
    }

};