//,temp,ResourceLimitManagerImpl.java,270,283,temp,ResourceLimitManagerImpl.java,254,268
//,3
public class xxx {
    @Override
    public void incrementResourceCount(long accountId, ResourceType type, Long... delta) {
        // don't upgrade resource count for system account
        if (accountId == Account.ACCOUNT_ID_SYSTEM) {
            s_logger.trace("Not incrementing resource count for system accounts, returning");
            return;
        }

        long numToIncrement = (delta.length == 0) ? 1 : delta[0].longValue();

        if (!updateResourceCountForAccount(accountId, type, true, numToIncrement)) {
            // we should fail the operation (resource creation) when failed to update the resource count
            throw new CloudRuntimeException("Failed to increment resource count of type " + type + " for account id=" + accountId);
        }
    }

};