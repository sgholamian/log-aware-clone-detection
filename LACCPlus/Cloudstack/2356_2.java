//,temp,AccountManagerImpl.java,449,469,temp,AccountManagerImpl.java,417,438
//,3
public class xxx {
    @Override
    public boolean isDomainAdmin(Long accountId) {
        if (accountId != null) {
            AccountVO acct = _accountDao.findById(accountId);
            if (acct == null) {
                return false;  //account is deleted or does not exist
            }
            for (SecurityChecker checker : _securityCheckers) {
                try {
                    if (checker.checkAccess(acct, null, null, "DomainCapability")) {
                        if (s_logger.isTraceEnabled()) {
                            s_logger.trace("DomainAdmin Access granted to " + acct + " by " + checker.getName());
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