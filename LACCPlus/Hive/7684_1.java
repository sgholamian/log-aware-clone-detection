//,temp,StatsSetupConst.java,329,342,temp,StatsSetupConst.java,256,276
//,3
public class xxx {
  public static void removeColumnStatsState(Map<String, String> params, List<String> colNames) {
    if (params == null) {
      return;
    }
    try {
      ColumnStatsAccurate stats = parseStatsAcc(params.get(COLUMN_STATS_ACCURATE));
      for (String string : colNames) {
        stats.columnStats.remove(string);
      }
      params.put(COLUMN_STATS_ACCURATE, ColumnStatsAccurate.objectWriter.writeValueAsString(stats));
    } catch (JsonProcessingException e) {
      LOG.trace(e.getMessage());
    }
  }

};