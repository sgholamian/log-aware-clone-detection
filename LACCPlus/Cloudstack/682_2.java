//,temp,AccountManagerImpl.java,417,438,temp,AccountManagerImpl.java,394,415
//,2
public class xxx {
    @Override
    public boolean isRootAdmin(Long accountId) {
        if (accountId != null) {
            AccountVO acct = _accountDao.findById(accountId);
            if (acct == null) {
                return false;  //account is deleted or does not exist
            }
            for (SecurityChecker checker : _securityCheckers) {
                try {
                    if (checker.checkAccess(acct, null, null, "SystemCapability")) {
                        if (s_logger.isTraceEnabled()) {
                            s_logger.trace("Root Access granted to " + acct + " by " + checker.getName());
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