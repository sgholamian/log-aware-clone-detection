//,temp,sample_1233.java,2,7,temp,sample_1234.java,2,8
//,3
public class xxx {
protected void serviceInit(Configuration conf) throws Exception {
this.limitOnPoolSize = conf.getInt( MRJobConfig.MR_AM_CONTAINERLAUNCHER_THREAD_COUNT_LIMIT, MRJobConfig.DEFAULT_MR_AM_CONTAINERLAUNCHER_THREAD_COUNT_LIMIT);


log.info("upper limit on the thread pool size is");
}

};