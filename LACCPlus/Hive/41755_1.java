//,temp,TestUpdateDeleteSemanticAnalyzer.java,179,188,temp,TestUpdateDeleteSemanticAnalyzer.java,158,166
//,3
public class xxx {
  @Test
  public void testUpdateOnePartition() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update U set b = 5 where ds = 'today'",
          "testUpdateOnePartition");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};