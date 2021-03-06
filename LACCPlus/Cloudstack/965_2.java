//,temp,AccountManagerImpl.java,2861,2872,temp,AccountManagerImpl.java,2846,2859
//,3
public class xxx {
    @Override
    public void checkAccess(Account account, DiskOffering dof) throws PermissionDeniedException {
        for (SecurityChecker checker : _securityCheckers) {
            if (checker.checkAccess(account, dof)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + account + " to " + dof + " by " + checker.getName());
                }
                return;
            }
        }

        assert false : "How can all of the security checkers pass on checking this caller?";
        throw new PermissionDeniedException("There's no way to confirm " + account + " has access to " + dof);
    }

};