//,temp,sample_3005.java,2,17,temp,sample_3004.java,2,18
//,3
public class xxx {
public void dummy_method(){
instancesCache = new PathChildrenCache(zooKeeperClient, workersPath, true);
instancesCache.getListenable().addListener(new InstanceStateChangeListener(), tp);
try {
instancesCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
this.instancesCache = instancesCache;
return instancesCache;
} catch (InvalidACLException e) {
CloseableUtils.closeQuietly(instancesCache);
long elapsedNs = System.nanoTime() - startTimeNs;
if (deltaNs == 0 || deltaNs <= elapsedNs) {


log.info("unable to start curator pathchildrencache");
}
}
}

};