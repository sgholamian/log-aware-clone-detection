//,temp,AccountManagerImpl.java,949,984,temp,AccountManagerImpl.java,646,665
//,3
public class xxx {
    @Override
    public boolean disableAccount(long accountId) throws ConcurrentOperationException, ResourceUnavailableException {
        boolean success = false;
        if (accountId <= 2) {
            if (s_logger.isInfoEnabled()) {
                s_logger.info("disableAccount -- invalid account id: " + accountId);
            }
            return false;
        }

        AccountVO account = _accountDao.findById(accountId);
        if ((account == null) || (account.getState().equals(State.disabled) && !account.getNeedsCleanup())) {
            success = true;
        } else {
            AccountVO acctForUpdate = _accountDao.createForUpdate();
            acctForUpdate.setState(State.disabled);
            success = _accountDao.update(Long.valueOf(accountId), acctForUpdate);

            if (success) {
                boolean disableAccountResult = false;
                try {
                    disableAccountResult = doDisableAccount(accountId);
                } finally {
                    if (!disableAccountResult) {
                        s_logger.warn("Failed to disable account " + account + " resources as a part of disableAccount call, marking the account for cleanup");
                        _accountDao.markForCleanup(accountId);
                    } else {
                        acctForUpdate = _accountDao.createForUpdate();
                        account.setNeedsCleanup(false);
                        _accountDao.update(accountId, account);
                    }
                }
            }
        }
        return success;
    }

};