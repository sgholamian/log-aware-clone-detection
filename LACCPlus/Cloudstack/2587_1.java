//,temp,NetworkACLServiceImpl.java,483,513,temp,NetworkACLManagerImpl.java,175,215
//,3
public class xxx {
    protected Long createAclListForNetworkAndReturnAclListId(CreateNetworkACLCmd aclItemCmd, Network network) {
        s_logger.debug("Network " + network.getId() + " is not associated with any ACL. Creating an ACL before adding acl item");

        if (!networkModel.areServicesSupportedByNetworkOffering(network.getNetworkOfferingId(), Network.Service.NetworkACL)) {
            throw new InvalidParameterValueException("Network Offering does not support NetworkACL service");
        }

        Vpc vpc = _entityMgr.findById(Vpc.class, network.getVpcId());
        if (vpc == null) {
            throw new InvalidParameterValueException("Unable to find Vpc associated with the Network");
        }

        String aclName = "VPC_" + vpc.getName() + "_Tier_" + network.getName() + "_ACL_" + network.getUuid();
        String description = "ACL for " + aclName;
        NetworkACL acl = _networkAclMgr.createNetworkACL(aclName, description, network.getVpcId(), aclItemCmd.isDisplay());
        if (acl == null) {
            throw new CloudRuntimeException("Error while create ACL before adding ACL Item for network " + network.getId());
        }
        s_logger.debug("Created ACL: " + aclName + " for network " + network.getId());
        Long aclId = acl.getId();
        //Apply acl to network
        try {
            if (!_networkAclMgr.replaceNetworkACL(acl, (NetworkVO)network)) {
                throw new CloudRuntimeException("Unable to apply auto created ACL to network " + network.getId());
            }
            s_logger.debug("Created ACL is applied to network " + network.getId());
        } catch (ResourceUnavailableException e) {
            throw new CloudRuntimeException("Unable to apply auto created ACL to network " + network.getId(), e);
        }
        return aclId;
    }

};