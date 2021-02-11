//,temp,NetworkACLManagerImpl.java,307,334,temp,NetworkACLManagerImpl.java,275,305
//,3
public class xxx {
    @Override
    public boolean revokeACLItemsForPrivateGw(final PrivateGateway gateway) throws ResourceUnavailableException {
        final long networkACLId = gateway.getNetworkACLId();
        final List<NetworkACLItemVO> aclItems = _networkACLItemDao.listByACL(networkACLId);
        if (aclItems.isEmpty()) {
            s_logger.debug("Found no network ACL Items for private gateway 'id=" + gateway.getId() + "'");
            return true;
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + aclItems.size() + " Network ACL Items for private gateway  id=" + gateway.getId());
        }

        for (final NetworkACLItemVO aclItem : aclItems) {
            // Mark all Network ACLs rules as Revoke, but don't update in DB
            if (aclItem.getState() == State.Add || aclItem.getState() == State.Active) {
                aclItem.setState(State.Revoke);
            }
        }

        final boolean success = applyACLToPrivateGw(gateway, aclItems);

        if (s_logger.isDebugEnabled() && success) {
            s_logger.debug("Successfully released Network ACLs for private gateway id=" + gateway.getId() + " and # of rules now = " + aclItems.size());
        }

        return success;
    }

};