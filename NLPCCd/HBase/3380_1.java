//,temp,sample_4095.java,2,16,temp,sample_5038.java,2,16
//,3
public class xxx {
public void dummy_method(){
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {
return;
}
if (context.isStopping()) {
return;
}
HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem( regions.toArray(new HRegionInfo[regions.size()]));


log.info("splitting region");
}

};