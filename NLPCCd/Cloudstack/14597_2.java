//,temp,sample_7583.java,2,19,temp,sample_7586.java,2,19
//,2
public class xxx {
public void dummy_method(){
Set<Long> zoneSet = new HashSet<Long>();
Collections.shuffle(imageStores);
for (DataStore imageStore : imageStores) {
Long zoneId = imageStore.getScope().getScopeId();
if (zoneId != null) {
DataCenterVO zone = _dcDao.findById(zoneId);
if (zone == null) {
continue;
}
if (Grouping.AllocationState.Disabled == zone.getAllocationState()) {


log.info("zone is disabled so skip downloading template to its image store");
}
}
}
}

};