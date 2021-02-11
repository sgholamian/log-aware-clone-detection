//,temp,RouterDeploymentDefinition.java,254,261,temp,VpcRouterDeploymentDefinition.java,84,92
//,3
public class xxx {
    protected void unlock() {
        if (tableLockId != null) {
            networkDao.releaseFromLockTable(tableLockId);
            if (logger.isDebugEnabled()) {
                logger.debug("Lock is released for network id " + tableLockId + " as a part of router startup in " + dest);
            }
        }
    }

};