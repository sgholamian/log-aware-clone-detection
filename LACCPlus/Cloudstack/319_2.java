//,temp,QuotaServiceImpl.java,287,309,temp,QuotaManagerImpl.java,276,297
//,3
public class xxx {
    private boolean saveQuotaAccount(final AccountVO account, final BigDecimal aggrUsage, final Date endDate) {
        // update quota_accounts
        QuotaAccountVO quota_account = _quotaAcc.findByIdQuotaAccount(account.getAccountId());

        if (quota_account == null) {
            quota_account = new QuotaAccountVO(account.getAccountId());
            quota_account.setQuotaBalance(aggrUsage);
            quota_account.setQuotaBalanceDate(endDate);
            if (s_logger.isDebugEnabled()) {
                s_logger.debug(quota_account);
            }
            _quotaAcc.persistQuotaAccount(quota_account);
            return true;
        } else {
            quota_account.setQuotaBalance(aggrUsage);
            quota_account.setQuotaBalanceDate(endDate);
            if (s_logger.isDebugEnabled()) {
                s_logger.debug(quota_account);
            }
            return _quotaAcc.updateQuotaAccount(account.getAccountId(), quota_account);
        }
    }

};