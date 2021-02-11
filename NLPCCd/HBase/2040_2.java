//,temp,sample_3362.java,2,18,temp,sample_3365.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (updatedLocations.isEmpty()) {
removed = tableLocations.remove(location.getRegion().getStartKey(), regionLocations);
} else {
removed = tableLocations.replace(location.getRegion().getStartKey(), regionLocations, updatedLocations);
}
if (removed) {
if (metrics != null) {
metrics.incrMetaCacheNumClearRegion();
}
if (LOG.isTraceEnabled()) {


log.info("removed from cache");
}
}
}

};