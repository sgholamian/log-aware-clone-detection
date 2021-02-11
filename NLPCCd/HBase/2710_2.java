//,temp,sample_5888.java,2,13,temp,sample_4096.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (regions == null || regions.isEmpty()) {
return;
}
if (context.isStopping()) {
return;
}
HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem( regions.toArray(new HRegionInfo[regions.size()]));
try {
admin.splitRegion(region.getRegionName());
} catch (Exception ex) {


log.info("split failed might be caused by other chaos");
}
}

};