//,temp,QuotaAlertManagerImpl.java,251,278,temp,AccountManagerImpl.java,646,665
//,3
public class xxx {
    protected boolean lockAccount(long accountId) {
        final short opendb = TransactionLegacy.currentTxn().getDatabaseId();
        boolean success = false;
        try (TransactionLegacy txn = TransactionLegacy.open(TransactionLegacy.CLOUD_DB)) {
            Account account = _accountDao.findById(accountId);
            if (account != null) {
                if (account.getState() == State.locked) {
                    return true; // already locked, no-op
                } else if (account.getState() == State.enabled) {
                    AccountVO acctForUpdate = _accountDao.createForUpdate();
                    acctForUpdate.setState(State.locked);
                    success = _accountDao.update(Long.valueOf(accountId), acctForUpdate);
                } else {
                    if (s_logger.isInfoEnabled()) {
                        s_logger.info("Attempting to lock a non-enabled account, current state is " + account.getState() + " (accountId: " + accountId + "), locking failed.");
                    }
                }
            } else {
                s_logger.warn("Failed to lock account " + accountId + ", account not found.");
            }
        } catch (Exception e) {
            s_logger.error("Exception occured while locking account by Quota Alert Manager", e);
            throw e;
        } finally {
            TransactionLegacy.open(opendb).close();
        }
        return success;
    }

};