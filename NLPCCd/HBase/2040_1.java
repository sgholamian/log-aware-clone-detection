//,temp,sample_3362.java,2,18,temp,sample_3365.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (updatedLocations.isEmpty()) {
removed = tableLocations.remove(startKey, regionLocations);
} else {
removed = tableLocations.replace(startKey, regionLocations, updatedLocations);
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