//,temp,sample_4899.java,2,11,temp,sample_3715.java,2,11
//,2
public class xxx {
protected void unlock() {
if (tableLockId != null) {
vpcDao.releaseFromLockTable(tableLockId);
if (logger.isDebugEnabled()) {


log.info("lock is released for vpc id as a part of router startup in");
}
}
}

};