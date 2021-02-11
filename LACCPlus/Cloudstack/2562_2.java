//,temp,NetworkACLManagerImpl.java,307,334,temp,NetworkACLManagerImpl.java,275,305
//,3
public class xxx {
    @Override
    public boolean revokeACLItemsForNetwork(final long networkId) throws ResourceUnavailableException {
        final Network network = _networkDao.findById(networkId);
        if (network.getNetworkACLId() == null) {
            return true;
        }
        final List<NetworkACLItemVO> aclItems = _networkACLItemDao.listByACL(network.getNetworkACLId());
        if (aclItems.isEmpty()) {
            s_logger.debug("Found no network ACL Items for network id=" + networkId);
            return true;
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + aclItems.size() + " Network ACL Items for network id=" + networkId);
        }

        for (final NetworkACLItemVO aclItem : aclItems) {
            // Mark all Network ACLs rules as Revoke, but don't update in DB
            if (aclItem.getState() == State.Add || aclItem.getState() == State.Active) {
                aclItem.setState(State.Revoke);
            }
        }

        final boolean success = applyACLItemsToNetwork(network.getId(), aclItems);

        if (s_logger.isDebugEnabled() && success) {
            s_logger.debug("Successfully released Network ACLs for network id=" + networkId + " and # of rules now = " + aclItems.size());
        }

        return success;
    }

};