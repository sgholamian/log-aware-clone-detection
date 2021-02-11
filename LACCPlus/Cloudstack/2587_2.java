//,temp,NetworkACLServiceImpl.java,483,513,temp,NetworkACLManagerImpl.java,175,215
//,3
public class xxx {
    @Override
    public boolean replaceNetworkACL(final NetworkACL acl, final NetworkVO network) throws ResourceUnavailableException {

        final NetworkOffering guestNtwkOff = _entityMgr.findById(NetworkOffering.class, network.getNetworkOfferingId());

        if (guestNtwkOff == null) {
            throw new InvalidParameterValueException("Can't find network offering associated with network: " + network.getUuid());
        }

        //verify that ACLProvider is supported by network offering
        if (!_ntwkModel.areServicesSupportedByNetworkOffering(guestNtwkOff.getId(), Service.NetworkACL)) {
            throw new InvalidParameterValueException("Cannot apply NetworkACL. Network Offering does not support NetworkACL service");
        }

        if (network.getNetworkACLId() != null) {
            //Revoke ACL Items of the existing ACL if the new ACL is empty
            //Existing rules won't be removed otherwise
            final List<NetworkACLItemVO> aclItems = _networkACLItemDao.listByACL(acl.getId());
            if (aclItems == null || aclItems.isEmpty()) {
                s_logger.debug("New network ACL is empty. Revoke existing rules before applying ACL");
                if (!revokeACLItemsForNetwork(network.getId())) {
                    throw new CloudRuntimeException("Failed to replace network ACL. Error while removing existing ACL items for network: " + network.getId());
                }
            }
        }

        network.setNetworkACLId(acl.getId());
        //Update Network ACL
        if (_networkDao.update(network.getId(), network)) {
            s_logger.debug("Updated network: " + network.getId() + " with Network ACL Id: " + acl.getId() + ", Applying ACL items");
            //Apply ACL to network
            final Boolean result = applyACLToNetwork(network.getId());
            if (result) {
                // public message on message bus, so that network elements implementing distributed routing capability
                // can act on the event
                _messageBus.publish(_name, "Network_ACL_Replaced", PublishScope.LOCAL, network);
            }
            return result;
        }
        return false;
    }

};