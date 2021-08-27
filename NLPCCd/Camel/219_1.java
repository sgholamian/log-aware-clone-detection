//,temp,sample_926.java,2,14,temp,sample_919.java,2,14
//,3
public class xxx {
public void onThreadPoolRemove(CamelContext camelContext, ThreadPoolExecutor threadPool) {
if (!initialized) {
return;
}
Object mtp = managedThreadPools.remove(threadPool);
if (mtp != null) {
if (!getManagementStrategy().isManaged(mtp, null)) {


log.info("the thread pool is not managed");
}
}
}

};