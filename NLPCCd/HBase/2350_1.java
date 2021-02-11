//,temp,sample_4094.java,2,11,temp,sample_473.java,2,11
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {


log.info("table doesn t have regions to split");
}
}

};