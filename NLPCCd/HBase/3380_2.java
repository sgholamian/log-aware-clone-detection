//,temp,sample_4095.java,2,16,temp,sample_5038.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (sleepTime > 0) {
Thread.sleep(sleepTime);
}
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {
return;
}
HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem( regions.toArray(new HRegionInfo[regions.size()]));


log.info("unassigning region");
}

};