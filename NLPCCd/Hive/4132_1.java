//,temp,sample_3882.java,2,9,temp,sample_2896.java,2,8
//,3
public class xxx {
private ExecutorService buildBasicStatsExecutor() {
int poolSize = conf.getInt(ConfVars.HIVE_MOVE_FILES_THREAD_COUNT.varname, 1);
poolSize = Math.max(poolSize, 1);
final ExecutorService pool = Executors.newFixedThreadPool(poolSize, new ThreadFactoryBuilder().setDaemon(true).setNameFormat("stats-updater-thread-%d").build());


log.info("getting file stats of all partitions threadpool size");
}

};