//,temp,BasicStatsNoJobTask.java,114,122,temp,BasicStatsTask.java,95,101
//,3
public class xxx {
  @Override
  public int process(Hive db, Table tbl) throws Exception {

    LOG.info("Executing stats (no job) task");

    ExecutorService threadPool = StatsTask.newThreadPool(conf);

    return aggregateStats(threadPool, db);
  }

};