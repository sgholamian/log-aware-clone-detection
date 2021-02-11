//,temp,AccountManagerImpl.java,2846,2859,temp,ConfigurationManagerImpl.java,4242,4257
//,3
public class xxx {
    @Override
    public void checkDiskOfferingAccess(final Account caller, final DiskOffering dof) {
        for (final SecurityChecker checker : _secChecker) {
            if (checker.checkAccess(caller, dof)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + caller + " to disk offering:" + dof.getId() + " by " + checker.getName());
                }
                return;
            } else {
                throw new PermissionDeniedException("Access denied to " + caller + " by " + checker.getName());
            }
        }

        assert false : "How can all of the security checkers pass on checking this caller?";
        throw new PermissionDeniedException("There's no way to confirm " + caller + " has access to disk offering:" + dof.getId());
    }

};