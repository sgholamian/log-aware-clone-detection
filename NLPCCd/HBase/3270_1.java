//,temp,sample_3247.java,2,11,temp,sample_3251.java,2,11
//,3
public class xxx {
public void testTablesInheritSnapshotSize() throws Exception {
TableName tn = helper.createTableWithRegions(1);
QuotaSettings settings = QuotaSettingsFactory.limitTableSpace( tn, SpaceQuotaHelperForTests.ONE_GIGABYTE, SpaceViolationPolicy.NO_INSERTS);
admin.setQuota(settings);
final long initialSize = 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
helper.writeData(tn, initialSize);


log.info("waiting until table size reflects written data");
}

};