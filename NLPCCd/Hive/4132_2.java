//,temp,sample_3882.java,2,9,temp,sample_2896.java,2,8
//,3
public class xxx {
public static ExecutorService newThreadPool(HiveConf conf) {
int numThreads = HiveConf.getIntVar(conf, ConfVars.HIVE_STATS_GATHER_NUM_THREADS);
ExecutorService executor = Executors.newFixedThreadPool(numThreads, new ThreadFactoryBuilder().setDaemon(true).setNameFormat("StatsNoJobTask-Thread-%d").build());


log.info("initialized threadpool for stats computation with threads");
}

};