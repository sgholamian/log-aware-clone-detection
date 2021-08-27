//,temp,TestUpdateDeleteSemanticAnalyzer.java,201,212,temp,TestUpdateDeleteSemanticAnalyzer.java,190,199
//,3
public class xxx {
  @Test
  public void testUpdateOnePartitionWhere() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update U set b = 5 where ds = 'today' and b > 5",
          "testUpdateOnePartitionWhere");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};