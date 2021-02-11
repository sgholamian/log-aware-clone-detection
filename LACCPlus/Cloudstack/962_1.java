//,temp,AccountManagerImpl.java,2831,2844,temp,AccountManagerImpl.java,482,493
//,3
public class xxx {
    @Override
    public void checkAccess(Account account, ServiceOffering so) throws PermissionDeniedException {
        for (SecurityChecker checker : _securityCheckers) {
            if (checker.checkAccess(account, so)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + account + " to " + so + " by " + checker.getName());
                }
                return;
            }
        }

        assert false : "How can all of the security checkers pass on checking this caller?";
        throw new PermissionDeniedException("There's no way to confirm " + account + " has access to " + so);
    }

};