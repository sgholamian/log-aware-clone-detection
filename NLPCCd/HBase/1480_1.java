//,temp,sample_1146.java,2,11,temp,sample_473.java,2,11
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.size() < 2) {


log.info("table doesn t have enough regions to merge");
}
}

};