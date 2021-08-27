//,temp,BasicStatsNoJobTask.java,114,122,temp,BasicStatsTask.java,95,101
//,3
public class xxx {
  @Override
  public int process(Hive db, Table tbl) throws Exception {

    LOG.info("Executing stats task");
    table = tbl;
    return aggregateStats(db);
  }

};