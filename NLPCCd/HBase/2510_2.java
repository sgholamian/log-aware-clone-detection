//,temp,sample_3247.java,2,11,temp,sample_3366.java,2,18
//,3
public class xxx {
public void dummy_method(){
TableName tn = helper.createTableWithRegions(10);
final long sizeLimit = 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
final SpaceViolationPolicy violationPolicy = SpaceViolationPolicy.NO_INSERTS;
QuotaSettings settings = QuotaSettingsFactory.limitTableSpace(tn, sizeLimit, violationPolicy);
TEST_UTIL.getAdmin().setQuota(settings);
helper.writeData(tn, 3L * SpaceQuotaHelperForTests.ONE_MEGABYTE);
Map<TableName,SpaceQuotaSnapshot> quotaSnapshots = snapshotNotifier.copySnapshots();
boolean foundSnapshot = false;
while (!foundSnapshot) {
if (quotaSnapshots.isEmpty()) {


log.info("found no violated quotas sleeping and retrying current reports");
}
}
}

};