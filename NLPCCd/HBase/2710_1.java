//,temp,sample_5888.java,2,13,temp,sample_4096.java,2,17
//,3
public class xxx {
public void perform() throws Exception {
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {
return;
}
HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem( regions.toArray(new HRegionInfo[regions.size()]));


log.info("flushing region");
}

};