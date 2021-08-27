//,temp,TestUpdateDeleteSemanticAnalyzer.java,126,135,temp,TestUpdateDeleteSemanticAnalyzer.java,85,93
//,3
public class xxx {
  @Test
  public void testDeleteOnePartitionWhere() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("delete from U where ds = 'today' and a > 5",
          "testDeletePartitionWhere");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};