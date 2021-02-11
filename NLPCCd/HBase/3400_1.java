//,temp,sample_5889.java,2,17,temp,sample_5038.java,2,16
//,3
public class xxx {
public void dummy_method(){
HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
Admin admin = util.getAdmin();
List<HRegionInfo> regions = admin.getTableRegions(tableName);
if (regions == null || regions.isEmpty()) {
return;
}
HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem( regions.toArray(new HRegionInfo[regions.size()]));
try {
admin.flushRegion(region.getRegionName());
} catch (Exception ex) {


log.info("flush failed might be caused by other chaos");
}
}

};