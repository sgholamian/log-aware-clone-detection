//,temp,RouterDeploymentDefinition.java,254,261,temp,VpcRouterDeploymentDefinition.java,84,92
//,3
public class xxx {
    @Override
    protected void unlock() {
        if (tableLockId != null) {
            vpcDao.releaseFromLockTable(tableLockId);
            if (logger.isDebugEnabled()) {
                logger.debug("Lock is released for vpc id " + tableLockId + " as a part of router startup in " + dest);
            }
        }
    }

};