//,temp,sample_1383.java,2,13,temp,sample_1384.java,2,18
//,3
public class xxx {
public void run() {
final LockManager.MasterLock lock = master.getLockManager().createMasterLock( MobUtils.getTableLockName(tableName), LockType.EXCLUSIVE, this.getClass().getName() + ": mob compaction");
try {
for (ColumnFamilyDescriptor hcd : hcds) {
MobUtils.doMobCompaction(conf, fs, tableName, hcd, pool, allFiles, lock);
}
} catch (IOException e) {


log.info("failed to perform the mob compaction");
}
}

};