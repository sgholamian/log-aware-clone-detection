//,temp,sample_3254.java,2,14,temp,sample_3252.java,2,12
//,3
public class xxx {
public void testTablesWithSnapshots() throws Exception {
final Connection conn = TEST_UTIL.getConnection();
final SpaceViolationPolicy policy = SpaceViolationPolicy.NO_INSERTS;
final TableName tn = helper.createTableWithRegions(10);
final long tableLimit = 3L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
TEST_UTIL.getAdmin().setQuota(QuotaSettingsFactory.limitTableSpace(tn, tableLimit, policy));
helper.writeData(tn, 1L * SpaceQuotaHelperForTests.ONE_MEGABYTE, "q1");


log.info("creating snapshot");
}

};