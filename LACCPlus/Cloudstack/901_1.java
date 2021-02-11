//,temp,ConfigurationManagerImpl.java,5505,5529,temp,ConfigurationManagerImpl.java,5479,5503
//,3
public class xxx {
    @Override
    @DB
    public boolean releaseAccountSpecificVirtualRanges(final long accountId) {
        final List<AccountVlanMapVO> maps = _accountVlanMapDao.listAccountVlanMapsByAccount(accountId);
        if (maps != null && !maps.isEmpty()) {
            try {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(final TransactionStatus status) {
                        for (final AccountVlanMapVO map : maps) {
                            if (!releasePublicIpRange(map.getVlanDbId(), _accountMgr.getSystemUser().getId(), _accountMgr.getAccount(Account.ACCOUNT_ID_SYSTEM))) {
                                throw new CloudRuntimeException("Failed to release account specific virtual ip ranges for account id=" + accountId);
                            }
                        }
                    }
                });
            } catch (final CloudRuntimeException e) {
                s_logger.error(e);
                return false;
            }
        } else {
            s_logger.trace("Account id=" + accountId + " has no account specific virtual ip ranges, nothing to release");
        }
        return true;
    }

};