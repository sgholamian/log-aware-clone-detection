//,temp,sample_3368.java,2,18,temp,sample_3249.java,2,13
//,3
public class xxx {
public void dummy_method(){
QuotaSettings settings = QuotaSettingsFactory.limitNamespaceSpace(namespace, sizeLimit, violationPolicy);
admin.setQuota(settings);
helper.writeData(tn1, 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE);
admin.flush(tn1);
Map<TableName,SpaceQuotaSnapshot> snapshots = snapshotNotifier.copySnapshots();
for (int i = 0; i < 5; i++) {
assertEquals( "Should not see any quota violations after writing 2MB of data", 0, numSnapshotsInViolation(snapshots));
try {
Thread.sleep(DEFAULT_WAIT_MILLIS);
} catch (InterruptedException e) {


log.info("interrupted while sleeping");
}
}
}

};