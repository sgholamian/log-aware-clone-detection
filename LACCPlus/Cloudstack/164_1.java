//,temp,AccountManagerImpl.java,449,469,temp,AccountManagerImpl.java,394,415
//,3
public class xxx {
    public boolean isResourceDomainAdmin(Long accountId) {
        if (accountId != null) {
            AccountVO acct = _accountDao.findById(accountId);
            if (acct == null) {
                return false;  //account is deleted or does not exist
            }
            for (SecurityChecker checker : _securityCheckers) {
                try {
                    if (checker.checkAccess(acct, null, null, "DomainResourceCapability")) {
                        if (s_logger.isTraceEnabled()) {
                            s_logger.trace("ResourceDomainAdmin Access granted to " + acct + " by " + checker.getName());
                        }
                        return true;
                    }
                } catch (PermissionDeniedException ex) {
                    return false;
                }
            }
        }
        return false;
    }

};