//,temp,SemanticAnalyzer.java,15272,15283,temp,SemanticAnalyzer.java,15258,15270
//,3
public class xxx {
  @Override
  public void startAnalysis() {
    String queryId = conf.getVar(HiveConf.ConfVars.HIVEQUERYID);
    SessionState ss = SessionState.get();
    if (ss == null) {
      LOG.info("No current SessionState, skipping metadata query-level caching for: {}", queryId);
      return;
    }
    if (conf.getBoolVar(ConfVars.HIVE_OPTIMIZE_HMS_QUERY_CACHE_ENABLED)) {
      LOG.info("Starting caching scope for: {}", queryId);
      ss.startScope(queryId);
    }
  }

};