//,temp,sample_3479.java,2,17,temp,sample_3478.java,2,17
//,3
public class xxx {
public void dummy_method(){
this.jobTimeoutConfigName = jobTimeoutConfigName;
this.requestType = requestType;
this.enableCancelTask = enableCancelTask;
int threads = !StringUtils.isEmpty(concurrentRequestsConfigName) ? appConf.getInt(concurrentRequestsConfigName, 0) : 0;
if (threads > 0) {
this.jobExecutePool = new ThreadPoolExecutor(threads, threads, threadKeepAliveTimeInHours, TimeUnit.HOURS, new SynchronousQueue<Runnable>());
this.jobExecutePool.allowCoreThreadTimeOut(true);
if (!StringUtils.isEmpty(jobTimeoutConfigName)) {
this.requestExecutionTimeoutInSec = appConf.getInt(jobTimeoutConfigName, 0);
}


log.info("configured threads for job request type with time out s");
}
}

};