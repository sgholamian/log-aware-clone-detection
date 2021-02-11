//,temp,sample_3247.java,2,11,temp,sample_3251.java,2,11
//,3
public class xxx {
public void testTablesWithSnapshots() throws Exception {
final Connection conn = TEST_UTIL.getConnection();
final SpaceViolationPolicy policy = SpaceViolationPolicy.NO_INSERTS;
final TableName tn = helper.createTableWithRegions(10);
final long tableLimit = 3L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
TEST_UTIL.getAdmin().setQuota(QuotaSettingsFactory.limitTableSpace(tn, tableLimit, policy));


log.info("writing first data set");
}

};