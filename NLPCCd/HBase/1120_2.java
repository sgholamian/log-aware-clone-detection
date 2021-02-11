//,temp,sample_198.java,2,12,temp,sample_543.java,2,13
//,3
public class xxx {
public String getStoragePolicyName(Path path) {
try {
Object blockStoragePolicySpi = ReflectionUtils.invokeMethod(this.fs, "getStoragePolicy", path);
return (String) ReflectionUtils.invokeMethod(blockStoragePolicySpi, "getName");
} catch (Exception e) {
if (LOG.isTraceEnabled()) {


log.info("failed to get policy directly");
}
}
}

};