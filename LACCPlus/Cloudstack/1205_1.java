//,temp,FirewallManagerImpl.java,833,853,temp,NetworkACLManagerImpl.java,262,273
//,3
public class xxx {
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

};