//,temp,sample_3368.java,2,18,temp,sample_3249.java,2,13
//,3
public class xxx {
public void testNamespacesInheritSnapshotSize() throws Exception {
String ns = helper.createNamespace().getName();
TableName tn = helper.createTableWithRegions(ns, 1);
QuotaSettings settings = QuotaSettingsFactory.limitNamespaceSpace( ns, SpaceQuotaHelperForTests.ONE_GIGABYTE, SpaceViolationPolicy.NO_INSERTS);
admin.setQuota(settings);
final long initialSize = 2L * SpaceQuotaHelperForTests.ONE_MEGABYTE;
helper.writeData(tn, initialSize);
admin.flush(tn);


log.info("waiting until namespace size reflects written data");
}

};