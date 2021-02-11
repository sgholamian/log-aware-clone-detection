//,temp,sample_5722.java,2,13,temp,sample_2030.java,2,17
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
boolean major = RandomUtils.nextInt(0, 100) < majorRatio;
LOG.info("Performing action: Compact random region of table " + tableName + ", major=" + major);
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {


log.info("table doesn t have regions to compact");
}
}

};