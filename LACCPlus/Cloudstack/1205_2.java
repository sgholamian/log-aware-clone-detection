//,temp,FirewallManagerImpl.java,833,853,temp,NetworkACLManagerImpl.java,262,273
//,3
public class xxx {
    @DB
    private void revokeRule(final NetworkACLItemVO rule) {
        if (rule.getState() == State.Staged) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Found a rule that is still in stage state so just removing it: " + rule);
            }
            _networkACLItemDao.remove(rule.getId());
        } else if (rule.getState() == State.Add || rule.getState() == State.Active) {
            rule.setState(State.Revoke);
            _networkACLItemDao.update(rule.getId(), rule);
        }
    }

};