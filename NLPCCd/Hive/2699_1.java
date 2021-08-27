//,temp,sample_3479.java,2,17,temp,sample_3478.java,2,17
//,3
public class xxx {
public void dummy_method(){
this.requestType = requestType;
this.enableCancelTask = enableCancelTask;
int threads = !StringUtils.isEmpty(concurrentRequestsConfigName) ? appConf.getInt(concurrentRequestsConfigName, 0) : 0;
if (threads > 0) {
this.jobExecutePool = new ThreadPoolExecutor(threads, threads, threadKeepAliveTimeInHours, TimeUnit.HOURS, new SynchronousQueue<Runnable>());
this.jobExecutePool.allowCoreThreadTimeOut(true);
if (!StringUtils.isEmpty(jobTimeoutConfigName)) {
this.requestExecutionTimeoutInSec = appConf.getInt(jobTimeoutConfigName, 0);
}
} else {


log.info("no thread pool configured for job request type");
}
}

};