//,temp,sample_3005.java,2,17,temp,sample_3004.java,2,18
//,3
public class xxx {
public void dummy_method(){
try {
instancesCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
this.instancesCache = instancesCache;
return instancesCache;
} catch (InvalidACLException e) {
CloseableUtils.closeQuietly(instancesCache);
long elapsedNs = System.nanoTime() - startTimeNs;
if (deltaNs == 0 || deltaNs <= elapsedNs) {
throw new IOException(e);
}


log.info("the cluster is not started yet invalidacl will retry");
}
}

};