//,temp,AccountManagerImpl.java,2831,2844,temp,AccountManagerImpl.java,482,493
//,3
public class xxx {
    @Override
    public void checkAccess(Account caller, Domain domain) throws PermissionDeniedException {
        for (SecurityChecker checker : _securityCheckers) {
            if (checker.checkAccess(caller, domain)) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Access granted to " + caller + " to " + domain + " by " + checker.getName());
                }
                return;
            }
        }
        throw new PermissionDeniedException("There's no way to confirm " + caller + " has access to " + domain);
    }

};