//,temp,sample_1257.java,2,11,temp,sample_4388.java,2,7
//,3
public class xxx {
public Set<String> scan(CamelContext camelContext) {
if (useRecovery) {
Set<String> scanned = Collections.unmodifiableSet(persistedCache.keySet());
return scanned;
} else {


log.info("what for to run recovery scans in context while repository is running in non recoverable aggregation repository mode");
}
}

};