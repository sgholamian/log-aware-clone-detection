//,temp,ConfigurationManagerImpl.java,4259,4274,temp,ConfigurationManagerImpl.java,4242,4257
//,3
public class xxx {
    @Override
    public void checkZoneAccess(final Account caller, final DataCenter zone) {
        for (final SecurityChecker checker : _secChecker) {
            if (checker.checkAccess(caller, zone)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + caller + " to zone:" + zone.getId() + " by " + checker.getName());
                }
                return;
            } else {
                throw new PermissionDeniedException("Access denied to " + caller + " by " + checker.getName() + " for zone " + zone.getId());
            }
        }

        assert false : "How can all of the security checkers pass on checking this caller?";
        throw new PermissionDeniedException("There's no way to confirm " + caller + " has access to zone:" + zone.getId());
    }

};