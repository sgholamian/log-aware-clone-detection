//,temp,StorageNetworkManagerImpl.java,282,302,temp,UserVmManagerImpl.java,2858,2883
//,3
public class xxx {
    @DB
    private InstanceGroupVO createVmGroup(String groupName, long accountId) {
        Account account = null;
        try {
            account = _accountDao.acquireInLockTable(accountId); // to ensure
            // duplicate
            // vm group
            // names are
            // not
            // created.
            if (account == null) {
                s_logger.warn("Failed to acquire lock on account");
                return null;
            }
            InstanceGroupVO group = _vmGroupDao.findByAccountAndName(accountId, groupName);
            if (group == null) {
                group = new InstanceGroupVO(groupName, accountId);
                group = _vmGroupDao.persist(group);
            }
            return group;
        } finally {
            if (account != null) {
                _accountDao.releaseFromLockTable(accountId);
            }
        }
    }

};