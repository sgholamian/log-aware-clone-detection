//,temp,ConfigurationManagerImpl.java,5505,5529,temp,ConfigurationManagerImpl.java,5479,5503
//,3
public class xxx {
     @Override
     @DB
     public boolean releaseDomainSpecificVirtualRanges(final long domainId) {
        final List<DomainVlanMapVO> maps = _domainVlanMapDao.listDomainVlanMapsByDomain(domainId);
        if (CollectionUtils.isNotEmpty(maps)) {
            try {
                Transaction.execute(new TransactionCallbackNoReturn() {
                    @Override
                    public void doInTransactionWithoutResult(final TransactionStatus status) {
                        for (DomainVlanMapVO map : maps) {
                            if (!releasePublicIpRange(map.getVlanDbId(), _accountMgr.getSystemUser().getId(), _accountMgr.getAccount(Account.ACCOUNT_ID_SYSTEM))) {
                                throw new CloudRuntimeException("Failed to release domain specific virtual ip ranges for domain id=" + domainId);
                            }
                        }
                    }
                });
            } catch (final CloudRuntimeException e) {
                s_logger.error(e);
                return false;
            }
        } else {
            s_logger.trace("Domain id=" + domainId + " has no domain specific virtual ip ranges, nothing to release");
        }
        return true;
    }

};