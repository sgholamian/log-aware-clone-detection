//,temp,sample_924.java,2,12,temp,sample_921.java,2,12
//,3
public class xxx {
public void onThreadPoolAdd(CamelContext camelContext, ThreadPoolExecutor threadPool, String id, String sourceId, String routeId, String threadPoolProfileId) {
if (!shouldRegister(threadPool, null)) {
return;
}
Object mtp = getManagementObjectStrategy().getManagedObjectForThreadPool(camelContext, threadPool, id, sourceId, routeId, threadPoolProfileId);
if (getManagementStrategy().isManaged(mtp, null)) {


log.info("the thread pool is already managed");
}
}

};