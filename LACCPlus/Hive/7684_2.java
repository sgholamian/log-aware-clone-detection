//,temp,StatsSetupConst.java,329,342,temp,StatsSetupConst.java,256,276
//,3
public class xxx {
  public static void setColumnStatsState(Map<String, String> params, List<String> colNames) {
    if (params == null) {
      throw new RuntimeException("params are null...cant set columnstatstate!");
    }
    if (colNames == null) {
      return;
    }
    ColumnStatsAccurate stats = parseStatsAcc(params.get(COLUMN_STATS_ACCURATE));

    for (String colName : colNames) {
      if (!stats.columnStats.containsKey(colName)) {
        stats.columnStats.put(colName, true);
      }
    }

    try {
      params.put(COLUMN_STATS_ACCURATE, ColumnStatsAccurate.objectWriter.writeValueAsString(stats));
    } catch (JsonProcessingException e) {
      LOG.trace(e.getMessage());
    }
  }

};