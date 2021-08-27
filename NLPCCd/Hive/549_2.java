//,temp,sample_2210.java,2,8,temp,sample_2530.java,2,11
//,3
public class xxx {
private List<HiveLock> lock(List<HiveLockObj> objs, int numRetriesForLock, long sleepTime) throws LockException {
sortLocks(objs);
if (LOG.isDebugEnabled()) {
for (HiveLockObj obj : objs) {


log.info("acquiring lock for with mode");
}
}
}

};