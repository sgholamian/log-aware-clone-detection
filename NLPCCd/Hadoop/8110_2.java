//,temp,sample_1233.java,2,7,temp,sample_1234.java,2,8
//,3
public class xxx {
protected void serviceInit(Configuration conf) throws Exception {
this.limitOnPoolSize = conf.getInt( MRJobConfig.MR_AM_CONTAINERLAUNCHER_THREAD_COUNT_LIMIT, MRJobConfig.DEFAULT_MR_AM_CONTAINERLAUNCHER_THREAD_COUNT_LIMIT);
this.initialPoolSize = conf.getInt( MRJobConfig.MR_AM_CONTAINERLAUNCHER_THREADPOOL_INITIAL_SIZE, MRJobConfig.DEFAULT_MR_AM_CONTAINERLAUNCHER_THREADPOOL_INITIAL_SIZE);


log.info("the thread pool initial size is");
}

};