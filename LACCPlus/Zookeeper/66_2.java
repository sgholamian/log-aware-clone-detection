//,temp,LeaderZooKeeperServer.java,107,113,temp,FollowerZooKeeperServer.java,133,139
//,2
public class xxx {
    @Override
    public int getGlobalOutstandingLimit() {
        int divisor = self.getQuorumSize() > 2 ? self.getQuorumSize() - 1 : 1;
        int globalOutstandingLimit = super.getGlobalOutstandingLimit() / divisor;
        LOG.info("Override {} to {}", GLOBAL_OUTSTANDING_LIMIT, globalOutstandingLimit);
        return globalOutstandingLimit;
    }

};