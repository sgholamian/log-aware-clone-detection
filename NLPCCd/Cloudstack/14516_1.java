//,temp,sample_4899.java,2,11,temp,sample_3715.java,2,11
//,2
public class xxx {
protected void unlock() {
if (tableLockId != null) {
networkDao.releaseFromLockTable(tableLockId);
if (logger.isDebugEnabled()) {


log.info("lock is released for network id as a part of router startup in");
}
}
}

};