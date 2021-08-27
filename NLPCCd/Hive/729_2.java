//,temp,sample_3549.java,2,9,temp,sample_3550.java,2,12
//,3
public class xxx {
private void printConflictingLocks(HiveLockObject key, HiveLockMode mode, Set<String> conflictingLocks) {
if (!conflictingLocks.isEmpty()) {
HiveLockObjectData requestedLock = new HiveLockObjectData(key.getData().toString());
for (String conflictingLock : conflictingLocks) {
HiveLockObjectData conflictingLockData = new HiveLockObjectData(conflictingLock);


log.info("conflicting lock to mode query queryid clientip");
}
}
}

};