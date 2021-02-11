//,temp,FirewallManagerImpl.java,825,855,temp,VpcManagerImpl.java,2183,2199
//,3
public class xxx {
    @Override
    @DB
    public void revokeRule(final FirewallRuleVO rule, Account caller, long userId, final boolean needUsageEvent) {
        if (caller != null) {
            _accountMgr.checkAccess(caller, null, true, rule);
        }

        Transaction.execute(new TransactionCallbackNoReturn() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
        boolean generateUsageEvent = false;

        if (rule.getState() == State.Staged) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Found a rule that is still in stage state so just removing it: " + rule);
            }
            removeRule(rule);
            generateUsageEvent = true;
        } else if (rule.getState() == State.Add || rule.getState() == State.Active) {
            rule.setState(State.Revoke);
            _firewallDao.update(rule.getId(), rule);
            generateUsageEvent = true;
        }

        if (generateUsageEvent && needUsageEvent) {
                    UsageEventUtils.publishUsageEvent(EventTypes.EVENT_NET_RULE_DELETE, rule.getAccountId(), 0, rule.getId(), null, rule.getClass().getName(),
                        rule.getUuid());
        }
            }
        });
    }

};