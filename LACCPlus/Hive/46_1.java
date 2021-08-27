//,temp,SemanticAnalyzer.java,15272,15283,temp,SemanticAnalyzer.java,15258,15270
//,3
public class xxx {
  @Override
  public void endAnalysis() {
    SessionState ss = SessionState.get();
    if (ss == null) {
      return;
    }
    if (conf.getBoolVar(ConfVars.HIVE_OPTIMIZE_HMS_QUERY_CACHE_ENABLED)) {
      String queryId = conf.getVar(HiveConf.ConfVars.HIVEQUERYID);
      LOG.info("Ending caching scope for: {}", queryId);
      ss.endScope(queryId);
    }
  }

};